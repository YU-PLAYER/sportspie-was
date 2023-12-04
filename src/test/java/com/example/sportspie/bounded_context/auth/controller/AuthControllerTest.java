package com.example.sportspie.bounded_context.auth.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sportspie.base.jwt.dto.JwtDto;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.auth.dto.OAuthTokenDto;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.AuthService;
import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private AuthService authService;

	@MockBean
	private JwtProvider jwtProvider;

	@Test
	void signIn() throws Exception {
		// Given: 테스트를 위한 준비
		JwtDto jwtDto = JwtDto.builder()
				.accessToken("my.awesome.access.token")
				.refreshToken("my.awesome.refresh.token")
				.build();
		OAuthTokenDto oAuthTokenDto = new OAuthTokenDto("my_awesome_token");

		// When: 테스트를 하고자 하는 행위
		when(authService.signIn(OAuthPlatform.NAVER, oAuthTokenDto)).thenReturn(jwtDto);

		// Then: 테스트 결과 검증
		mockMvc.perform(post("/api/auth/sign-in/naver")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(oAuthTokenDto)))
				.andExpect(status().isOk());
	}

	@Test
	void signInWithToken() throws Exception {
		// Given
		String code = "my_awesome_code";
		OAuthTokenDto oAuthTokenDto = new OAuthTokenDto("my_awesome_token");

		// When
		when(authService.signInWithToken(OAuthPlatform.NAVER, code)).thenReturn(oAuthTokenDto);

		// Then
		mockMvc.perform(get("/api/auth/sign-in/naver/token?code=" + code)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(oAuthTokenDto)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value(oAuthTokenDto.getToken()));
	}

	@Test
	void withdrawal() throws Exception {
		// Given
		String accessToken = "my.awesome.access_token";
		User user = User.builder()
				.username("jinwoo")
				.nickname("이진우")
				.build();
		user.setId(1L);

		// When
		when(jwtProvider.resolveToken(any())).thenReturn(accessToken);
		when(jwtProvider.getUsername(anyString())).thenReturn(user.getUsername());
		when(authService.withdrawal(user.getId())).thenReturn(user);

		// Then
		mockMvc.perform(delete("/api/auth/withdrawal")
						.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}
}