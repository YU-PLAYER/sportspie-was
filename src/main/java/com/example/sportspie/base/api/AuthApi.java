package com.example.sportspie.base.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sportspie.base.jwt.dto.JwtDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenRequestDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "사용자 인증 관련 API")
public interface AuthApi {
	@PostMapping("/sign-in/{platform}")
	@Operation(summary = "OAuth 로그인 메서드", description = "사용자가 OAuth 로그인을 하기 위한 메서드입니다.")
	ResponseEntity<JwtDto> signIn(@PathVariable String platform, @RequestBody OAuthTokenRequestDto oAuthTokenDto);
}
