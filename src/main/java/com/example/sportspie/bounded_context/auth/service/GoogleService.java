package com.example.sportspie.bounded_context.auth.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.example.sportspie.bounded_context.auth.config.GoogleConfig;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenRequestDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthUserInfoResponseDto;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleService implements OAuthService {
	private final GoogleConfig googleConfig;
	private final JsonParser parser = new JsonParser();

	@Override
	public OAuthUserInfoResponseDto getUserInfo(OAuthTokenRequestDto oAuthTokenRequestDto) {
		try {
			String token = oAuthTokenRequestDto.getToken();

			URL url = new URL(googleConfig.getUserInfoUri());
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("GET");
			conn.setDoOutput(true);

			conn.setRequestProperty("Authorization", "Bearer " + token);

			int responseCode = conn.getResponseCode();

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			br.close();

			JsonElement responseElement = parser.parse(result).getAsJsonObject();

			String id = getStringOrNull(responseElement, "sub");
			String email = getStringOrNull(responseElement, "email");
			String nickname = getStringOrNull(responseElement, "name");

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
