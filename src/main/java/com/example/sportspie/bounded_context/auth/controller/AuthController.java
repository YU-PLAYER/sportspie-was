package com.example.sportspie.bounded_context.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.sportspie.base.api.AuthApi;
import com.example.sportspie.base.jwt.dto.JwtDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenRequestDto;
import com.example.sportspie.bounded_context.auth.service.AuthService;
import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
	private final AuthService authService;

	@Override
	public ResponseEntity<JwtDto> signIn(String platform, OAuthTokenRequestDto oAuthTokenDto) {
		return ResponseEntity.ok(authService.signIn(stringToPlatform(platform), oAuthTokenDto));
	}

	private OAuthPlatform stringToPlatform(String platform) {
		switch (platform) {
			case "naver":
				return OAuthPlatform.NAVER;
			case "kakao":
				return OAuthPlatform.KAKAO;
			case "google":
				return OAuthPlatform.GOOGLE;
			default:
				new IllegalArgumentException("지원하지 않는 플랫폼입니다. platform=" + platform);
		}
		return null;
	}
}
