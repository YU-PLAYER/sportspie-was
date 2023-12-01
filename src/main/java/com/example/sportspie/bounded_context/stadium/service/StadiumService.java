package com.example.sportspie.bounded_context.stadium.service;

import com.example.sportspie.bounded_context.stadium.config.StadiumConfig;
import com.example.sportspie.bounded_context.stadium.dto.INearbyStadium;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.repository.StadiumRepository;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.google.gson.JsonElement;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumConfig stadiumConfig;

    private final StadiumRepository stadiumRepository;

    private final JsonParser parser = new JsonParser();


    public List<Stadium> list() { return stadiumRepository.findAll();}

    public Stadium read(Integer id) {
        return stadiumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 경기장이 없습니다. id= "+ id));
    }

    public List<INearbyStadium> list(String latitude, String longitude){
        return stadiumRepository.findNearbyStadium(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }

    @Transactional
    public void update() throws IOException, JsonIOException {
        String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?ServiceKey="+stadiumConfig.getServiceKey()+"&pageNo=1&numOfRows=14&dataType=JSON";
        LocalDateTime now = LocalDateTime.now();
        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//        String baseTime = now.format(DateTimeFormatter.ofPattern("HHmm"));
        String baseTime = "1100"; // 11시 날씨

        List<Stadium> stadiums = stadiumRepository.findAll();
        for(Stadium stadium: stadiums){
            LatXLngY rs = convertToGRID(stadium.getLatitude(), stadium.getLongitude());
            StringBuilder tmp_sb = new StringBuilder(baseUrl);
            tmp_sb.append("&base_date=").append(baseDate)
                    .append("&base_time=").append(baseTime)
                    .append("&nx=").append(rs.xtoString())
                    .append("&ny=").append(rs.ytoString());
            String apiUrl = tmp_sb.toString();

            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            int resCode = con.getResponseCode();
            String responseMessage = con.getResponseMessage();

            if (responseMessage.equals("OK")){
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while ((line = br.readLine()) != null){
                    sb.append(line);
                }

                br.close();

                JsonObject jsonObject = parser.parse(sb.toString()).getAsJsonObject();

                JsonArray itemList = jsonObject.getAsJsonObject("response")
                        .getAsJsonObject("body")
                        .getAsJsonObject("items")
                        .getAsJsonArray("item");

                int pty = 0;
                int sky = 1;
                for (JsonElement itemElement : itemList){
                    JsonObject item = itemElement.getAsJsonObject();
                    String category = item.get("category").getAsString();

                    if ("PTY".equals(category)){
                        pty = item.get("fcstValue").getAsInt();
                    } else if ("SKY".equals(category)) {
                        sky = item.get("fcstValue").getAsInt();
                    }
                }
                stadiumRepository.updateWeatherType(stadium.getId(), getWeatherType(pty, sky));
            }
        }
    }

//    pty: 강수 형태 코드, sky: 하늘 상태 코드
    private int getWeatherType(int pty, int sky){
        if (pty == 1 || pty == 2 || pty == 4) return 2; // 비
        else if (pty == 3) return 3; // 눈
        else if (sky == 1 || sky == 3) return 0; // 맑음
        else return 1; // 흐림
    }

//    기상청에서는 위도 경도를 사용하지 않고 행정구역 격자 좌표를 사용. 위경도 -> 격자좌표 변환하는 함수
    private LatXLngY convertToGRID(double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기1준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        rs.lat = lat_X;
        rs.lng = lng_Y;
        double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lng_Y * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;
        rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
        rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);

        return rs;
    }

    static class LatXLngY{
        public double lat;
        public double lng;
        public double x;
        public double y;
        public String xtoString(){
            return (int)x+"";
        }
        public String ytoString(){
            return (int)y+"";
        }
    }
}
