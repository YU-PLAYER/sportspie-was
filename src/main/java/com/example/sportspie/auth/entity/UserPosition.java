package com.example.sportspie.auth.entity;

import com.example.sportspie.auth.type.Position;
import com.example.sportspie.base.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPosition extends BaseTimeEntity {
	@JoinColumn(name = "user_id", nullable = false)
	@ManyToOne
	private User user;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Position position;

	@Builder
	public UserPosition(User user, Position position) {
		this.user = user;
		this.position = position;
	}
}
