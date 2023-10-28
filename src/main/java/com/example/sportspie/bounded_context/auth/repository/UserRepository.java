package com.example.sportspie.bounded_context.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportspie.bounded_context.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
