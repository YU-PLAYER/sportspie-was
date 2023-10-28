package com.example.sportspie.bounded_context.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthUserInfoResponseDto {
	private String id;
	private String email;
	private String nickname;
	private String profileImageUrl;

	@Builder
	public OAuthUserInfoResponseDto(String id, String email, String nickname, String profileImageUrl) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}
}
