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
import lombok.Builder;
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

	@Column(columnDefinition = "TINYINT")
	private GameResult result;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private Integer currentCapacity;

	@Builder
	public Game(User author, String title, Integer maxCapacity, LocalDateTime startedAt, Stadium stadium, String content, GameStatus status, GameResult result, Integer currentCapacity) {
		this.author = author;
		this.title = title;
		this.maxCapacity = maxCapacity;
		this.startedAt = startedAt;
		this.stadium = stadium;
		this.content = content;
		this.status = status;
		this.result = result;
		this.currentCapacity = currentCapacity;
	}

	public Boolean isAuthor(Long userId){
		return author.equals(userId);
	}

	/*
	경기 인원 확정 조건
	1. 경기 참여 인원 짝수
	 */
	public Boolean isSatisfiedCapacity(){
		return getCurrentCapacity() % 2 == 0;
	}

	/*
	경기 결과 확정 조건
	1. 경기 확정상태
	2. 경기 시작 시간 이후
	 */
	public Boolean isSatisfiedResult(){
		return getStartedAt().isAfter(LocalDateTime.now()) && getStatus().equals(GameStatus.PROGRESS);
	}

	/*
	기존 경기 삭제 조건
	1. 경기 예정 상태
	2. 경기 시작 시간 2시간 전
	 */
	public Boolean isSatisfiedDelete(){
		return getStartedAt().minusHours(2).isBefore(LocalDateTime.now()) && getStatus().equals(GameStatus.BEFORE);
	}

}
