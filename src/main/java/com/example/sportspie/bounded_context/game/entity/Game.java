package com.example.sportspie.bounded_context.game.entity;

import java.time.LocalDateTime;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;

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
public class Game extends BaseTimeEntity {
	@JoinColumn(name = "author_id", nullable = false)
	@ManyToOne
	private User author;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Integer maxCapacity;

	@Column(nullable = false)
	private LocalDateTime startedAt;

	@JoinColumn(name = "stadium_id", nullable = false)
	@ManyToOne
	private Stadium stadium;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private GameStatus status;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private GameResult result;
}
