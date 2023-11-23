package com.example.sportspie.bounded_context.auth.service;

import com.example.sportspie.bounded_context.auth.dto.OAuthTokenDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthUserInfoResponseDto;

public interface OAuthService {
	OAuthUserInfoResponseDto getUserInfo(OAuthTokenDto oAuthTokenDto);

	OAuthTokenDto getToken(String authorizationCode);
}
