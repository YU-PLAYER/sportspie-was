package com.example.sportspie.bounded_context.auth.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.sportspie.bounded_context.auth.dto.ImageUrlResponseDto;
import com.example.sportspie.bounded_context.auth.dto.UserInfoDto;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.repository.UserRepository;
import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final S3Service s3Service;
	private final UserRepository userRepository;

	public User create(User user) {
		return userRepository.save(user);
	}

	public User read(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
	}

	public User read(String username, OAuthPlatform oAuthPlatform) {
		return userRepository.findByUsernameAndPlatform(username, oAuthPlatform)
				.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. username=" + username));
	}

	public User update(Long userId, UserInfoDto userInfoDto) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
		userRepository.save(user.update(userInfoDto));
		return user;
	}

	public User delete(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));
		userRepository.delete(user);
		return user;
	}

	public boolean isExist(String username, OAuthPlatform oAuthPlatform) {
		return userRepository.findByUsernameAndPlatform(username, oAuthPlatform).isPresent();
	}

	public List<User> list() {
		return userRepository.findAll();
	}

	public ImageUrlResponseDto uploadImage(MultipartFile multipartFile) {
		return new ImageUrlResponseDto(s3Service.uploadFile(multipartFile));
	}
}
