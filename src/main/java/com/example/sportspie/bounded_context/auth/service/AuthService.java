package com.example.sportspie.bounded_context.auth.service;

import org.springframework.stereotype.Service;

import com.example.sportspie.base.jwt.dto.JwtDto;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenRequestDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthUserInfoResponseDto;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final OAuthBridge oAuthBridge;
	private final UserService userService;
	private final JwtProvider jwtProvider;

	public JwtDto signIn(OAuthPlatform platform, OAuthTokenRequestDto oAuthTokenRequestDto) {
		// 1. 사용자 정보 읽어오기
		OAuthUserInfoResponseDto userInfo = oAuthBridge.getService(platform).getUserInfo(oAuthTokenRequestDto);
		// 2. 저장 여부 체크 (저장이 되어있지 않으면 저장)
		if (!userService.isExist(userInfo.getId(), platform)) {
			User savedUser = User.builder()
					.username(userInfo.getId())
					.email(userInfo.getEmail())
					.nickname(userInfo.getNickname())
					.imageUrl(userInfo.getProfileImageUrl())
					.platform(platform)
					.build();
			userService.create(savedUser);
		}

		User user = userService.read(userInfo.getId(), platform);

		String accessToken = jwtProvider.generateToken(user.getUsername(), user.getId());
		String refreshToken = jwtProvider.generateRefreshToken(user.getUsername(), user.getId());

		// 3. JWT 반환
		return JwtDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}
}