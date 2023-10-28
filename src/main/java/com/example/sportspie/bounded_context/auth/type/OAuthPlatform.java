package com.example.sportspie.bounded_context.auth.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OAuthPlatform {
	NAVER(0, "naver"),
	KAKAO(1, "kakao"),
	GOOGLE(2, "google");

	private int id;
	private String name;
}
