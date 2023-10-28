package com.example.sportspie.bounded_context.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.type.OAuthPlatform;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsernameAndPlatform(String username, OAuthPlatform platform);
}
