package com.example.sportspie.bounded_context.notification.entity;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.notification.type.NotificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Notification extends BaseTimeEntity {
	@JoinColumn(name = "receiver_id", nullable = false)
	@ManyToOne
	private User receiver;

	@Column(nullable = false)
	private String content;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private NotificationType type;
}
