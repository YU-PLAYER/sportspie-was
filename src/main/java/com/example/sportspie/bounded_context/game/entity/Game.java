package com.example.sportspie.bounded_context.game.entity;

import java.time.LocalDateTime;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.dto.GameListResponseDto;
import com.example.sportspie.bounded_context.game.dto.GameResponseDto;
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
import org.hibernate.annotations.ColumnDefault;
import org.springframework.cglib.core.Local;

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
	@ColumnDefault("1")
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

	public GameListResponseDto toListDto(){
		return GameListResponseDto.builder()
				.gameId(getId())
				.gameStatus(status)
				.title(title)
				.time(startedAt.toLocalTime())
				.weather(stadium.getWeatherType())
				.stadiumName(stadium.getName())
				.totalPeople(maxCapacity)
				.currentPeople(currentCapacity)
				.build();
	}

	public GameResponseDto toDto(){
		return GameResponseDto.builder()
				.gameId(getId())
				.userId(author.getId())
				.title(title)
				.content(content)
				.stadium(stadium)
				.maxCapacity(maxCapacity)
				.currentCapacity(currentCapacity)
				.startedAt(startedAt)
				.status(status)
				.result(result)
				.build();
	}

	public Boolean isAuthor(User user){
		return author.equals(user);
	}

	/**
	 * 경기 인원 확정 조건
	 * 경기 참여 인원 짝수 (팀 구분 X)
	 * @return
	 */
	public Boolean isSatisfiedCapacity(){
		return getCurrentCapacity() % 2 == 0;
	}

	/**
	 * 경기 결과 확정 조건
	 * 경기 시작 시간이 지난 후 && 경기 인원 확정 상태
	 */
	public Boolean isSatisfiedResult(){
		return LocalDateTime.now().isAfter(getStartedAt()) && getStatus().equals(GameStatus.PROGRESS);
	}

	/**
	 * 기존 경기 삭제 조건
	 * 경기 2시간 전 && 경기 인원 미확정 상태
	 */
	public Boolean isSatisfiedDelete(){
		return LocalDateTime.now().plusHours(2).isBefore(getStartedAt()) && getStatus().equals(GameStatus.BEFORE);
	}

	/**
	 * 경기 참가 신청 조건
	 * 현재 인원 < 최대 인원
	 */
	public Boolean isSatisfiedJoin(){
		return maxCapacity - currentCapacity > 0 && LocalDateTime.now().isBefore(getStartedAt());
	}

	public void increaseCurrentCapacity(){
		this.currentCapacity += 1;
	}

	public void decreaseCurrentCapacity(){
		this.currentCapacity -= 1;
	}

}
