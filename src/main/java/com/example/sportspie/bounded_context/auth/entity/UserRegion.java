package com.example.sportspie.bounded_context.auth.entity;

import com.example.sportspie.base.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegion extends BaseTimeEntity {
	@JoinColumn(name = "user_id", nullable = false)
	@ManyToOne
	private User user;

	@Column(nullable = false)
	private String region;
}
