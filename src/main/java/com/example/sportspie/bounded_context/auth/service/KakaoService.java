package com.example.sportspie.bounded_context.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.sportspie.bounded_context.auth.config.KakaoConfig;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthUserInfoResponseDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoService implements OAuthService {
	private final KakaoConfig kakaoConfig;
	private final JsonParser parser = new JsonParser();

	@Override
	public OAuthUserInfoResponseDto getUserInfo(OAuthTokenDto oAuthTokenDto) {
		try {
			String token = oAuthTokenDto.getToken();

			URL url = new URL(kakaoConfig.getUserInfoUri());

			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			conn.setRequestProperty("Authorization", "Bearer " + token);

			int responseCode = conn.getResponseCode();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			JsonElement responseElement = parser.parse(result);
			String id = getStringOrNull(responseElement, "id");
			String email = getStringOrNull(responseElement.getAsJsonObject().get("kakao_account"), "email");
			String nickname = getStringOrNull(responseElement.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile"), "nickname");
			String profileImage = getStringOrNull(responseElement.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile"), "profile_image_url");

			return OAuthUserInfoResponseDto.builder()
					.id(id)
					.email(email)
					.nickname(nickname)
					.profileImageUrl(profileImage)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OAuthTokenDto getToken(String authorizationCode) {
		try {
			URL url = new URL(UriComponentsBuilder
					.fromUriString(kakaoConfig.getTokenUri())
					.queryParam("grant_type", "authorization_code")
					.queryParam("client_id", kakaoConfig.getClientId())
					.queryParam("client_secret", kakaoConfig.getClientSecret())
					.queryParam("code", authorizationCode)
					.build()
					.toString());

			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			BufferedReader br;

			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}

			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			br.close();

			JsonElement element = parser.parse(response.toString());

			return new OAuthTokenDto(element.getAsJsonObject().get("access_token").getAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getStringOrNull(JsonElement element, String fieldName) {
		JsonElement fieldElement = element.getAsJsonObject().get(fieldName);
		return !fieldElement.isJsonNull() ? fieldElement.getAsString() : null;
	}
}
