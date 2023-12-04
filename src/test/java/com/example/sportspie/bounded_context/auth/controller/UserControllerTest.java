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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.auth.dto.ImageUrlResponseDto;
import com.example.sportspie.bounded_context.auth.dto.UserInfoDto;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private JwtProvider jwtProvider;

	@Test
	void read() throws Exception {
		// Given
		String accessToken = "my.awesome.access_token";
		User user = User.builder()
				.username("jinwoo")
				.nickname("이진우")
				.build();
		user.setId(1L);

		// When
		when(jwtProvider.resolveToken(any())).thenReturn(accessToken);
		when(jwtProvider.getUsername(user.getUsername())).thenReturn(user.getUsername());
		when(userService.read(user.getId())).thenReturn(user);

		// Then
		mockMvc.perform(get("/api/user/me")
						.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}

	@Test
	void update() throws Exception {
		// Given
		String accessToken = "my.awesome.access_token";
		User user = User.builder()
				.username("jinwoo")
				.nickname("이진우")
				.build();
		user.setId(1L);

		UserInfoDto userInfoDto = UserInfoDto.builder()
				.nickname("이진우")
				.build();

		// When
		when(jwtProvider.resolveToken(any())).thenReturn(accessToken);
		when(jwtProvider.getUsername(user.getUsername())).thenReturn(user.getUsername());
		when(userService.update(user.getId(), userInfoDto)).thenReturn(user);

		// Then
		mockMvc.perform(put("/api/user/me")
						.header("Authorization", "Bearer " + accessToken)
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(userInfoDto)))
				.andExpect(status().isOk());
	}

	@Test
	void read2() throws Exception {
		// Given
		String accessToken = "my.awesome.access_token";
		User user = User.builder()
				.username("jinwoo")
				.nickname("이진우")
				.build();
		user.setId(1L);

		Long findUserId = 2L;
		User findUser = User.builder()
				.username("jinhak")
				.nickname("김진학")
				.build();

		// When
		when(jwtProvider.resolveToken(any())).thenReturn(accessToken);
		when(jwtProvider.getUsername(user.getUsername())).thenReturn(user.getUsername());
		when(userService.read(findUserId)).thenReturn(findUser);

		// Then
		mockMvc.perform(get("/api/user/" + findUserId)
						.header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}

	@Test
	void uploadImage() throws Exception {
		// Given
		String accessToken = "my.awesome.access_token";
		User user = User.builder()
				.username("jinwoo")
				.nickname("이진우")
				.build();
		user.setId(1L);
		ImageUrlResponseDto imageUrlResponseDto = new ImageUrlResponseDto("my.awesome.image.url");
		String fileName = "example.png";
		byte[] content = "Example content".getBytes();
		String paramName = "file";
		MockMultipartFile file = new MockMultipartFile(paramName, fileName, MediaType.TEXT_PLAIN_VALUE, content);

		// When
		when(jwtProvider.resolveToken(any())).thenReturn(accessToken);
		when(jwtProvider.getUsername(user.getUsername())).thenReturn(user.getUsername());
		when(userService.uploadImage(any())).thenReturn(imageUrlResponseDto);

		// Then
		mockMvc.perform(multipart("/api/user/image")
						.file(file)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.url").value(imageUrlResponseDto.getUrl()));
	}
}