package com.example.sportspie.base.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sportspie.base.jwt.dto.JwtDto;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenDto;
import com.example.sportspie.bounded_context.auth.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "사용자 인증 관련 API")
public interface AuthApi {
	@PostMapping("/sign-in/{platform}")
	@Operation(summary = "OAuth 로그인 메서드", description = "사용자가 OAuth 로그인을 하기 위한 메서드입니다.")
	ResponseEntity<JwtDto> signIn(@PathVariable String platform, @RequestBody OAuthTokenDto oAuthTokenDto);

	@GetMapping("/sign-in/{platform}/token")
	@Operation(summary = "OAuth 토큰 조회 메서드", description = "사용자가 플랫폼을 통해 OAuth 토큰을 조회하기 위한 메서드입니다.")
	ResponseEntity<OAuthTokenDto> signInWithToken(@PathVariable String platform, @RequestParam(name = "code") String code);

	@DeleteMapping("/withdrawal")
	@Operation(summary = "회원 탈퇴 메서드", description = "사용자가 회원 탈퇴를 하기 위한 메서드입니다.")
	ResponseEntity<User> withdrawal(HttpServletRequest request);
}
