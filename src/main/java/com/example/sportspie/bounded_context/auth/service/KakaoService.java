package com.example.sportspie.bounded_context.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.example.sportspie.bounded_context.auth.config.KakaoConfig;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenRequestDto;
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
	public OAuthUserInfoResponseDto getUserInfo(OAuthTokenRequestDto oAuthTokenRequestDto) {
		try {
			String token = oAuthTokenRequestDto.getToken();

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

			return OAuthUserInfoResponseDto.builder()
					.id(id)
					.email(email)
					.nickname(nickname)
					.build();
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
