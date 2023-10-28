package com.example.sportspie.bounded_context.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthUserInfoResponseDto {
	private String id;
	private String email;
	private String nickname;

	@Builder
	public OAuthUserInfoResponseDto(String id, String email, String nickname) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
	}
}
