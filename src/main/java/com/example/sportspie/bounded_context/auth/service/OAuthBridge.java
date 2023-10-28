package com.example.sportspie.bounded_context.auth.service;

import org.springframework.stereotype.Service;

import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthBridge {
	private final NaverService naverService;
	private final KakaoService kakaoService;
	private final GoogleService googleService;

	public OAuthService getService(OAuthPlatform platform) {
		return switch (platform) {
			case NAVER -> naverService;
			case KAKAO -> kakaoService;
			case GOOGLE -> googleService;
		};
	}
}
