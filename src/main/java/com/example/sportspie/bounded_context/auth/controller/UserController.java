package com.example.sportspie.bounded_context.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sportspie.base.api.UserApi;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.auth.dto.ImageUrlResponseDto;
import com.example.sportspie.bounded_context.auth.dto.UserInfoDto;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
	public final UserService userService;
	public final JwtProvider jwtProvider;

	@Override
	public ResponseEntity<User> read(HttpServletRequest request) {
		Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
		return ResponseEntity.ok(userService.read(userId));
	}

	@Override
	public ResponseEntity<User> update(HttpServletRequest request, UserInfoDto userInfoDto) {
		Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
		return ResponseEntity.ok(userService.update(userId, userInfoDto));
	}

	@Override
	public ResponseEntity<User> read(Long userId) {
		return ResponseEntity.ok(userService.read(userId));
	}

	@Override
	public ResponseEntity<ImageUrlResponseDto> uploadImage(MultipartFile multipartFile) {
		return ResponseEntity.ok(userService.uploadImage(multipartFile));
	}
}
