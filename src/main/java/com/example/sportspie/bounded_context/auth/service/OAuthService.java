package com.example.sportspie.bounded_context.auth.service;

import com.example.sportspie.bounded_context.auth.dto.OAuthTokenRequestDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthUserInfoResponseDto;

public interface OAuthService {
	OAuthUserInfoResponseDto getUserInfo(OAuthTokenRequestDto oAuthTokenRequestDto);
}
