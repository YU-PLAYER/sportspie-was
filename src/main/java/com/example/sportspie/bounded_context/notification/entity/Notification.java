package com.example.sportspie.bounded_context.notification.entity;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.notification.dto.NotificationResponseDto;
import com.example.sportspie.bounded_context.notification.type.NotificationType;

import jakarta.persistence.*;
import lombok.Builder;
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

	@JoinColumn(name = "from_game_id", nullable = false)
	@ManyToOne
	private Game fromGame;

	@Column(nullable = false)
	private String content;

	@Column(columnDefinition = "TINYINT", nullable = false)
	private NotificationType type;

	@Builder
	public Notification(User receiver, Game fromGame, NotificationType type) {
		this.receiver = receiver;
		this.fromGame = fromGame;
		this.type = type;
		switch (type){
			case GAME_CONFIRMED:
               	this.content = "경기가 확정되었습니다.";
                break;
            case RESULTS_CONFIRMED:
                this.content = "경기결과가 확정되었습니다.";
                break;
            case DATE_IMMINENT:
                this.content = "경기 시작이 임박했습니다.";
                break;
            case REPORTED:
                this.content = "신고가 완료되었습니다.";
                break;
		}
	}
	public NotificationResponseDto toDto(){
		return NotificationResponseDto.builder()
				.type(type)
				.date(fromGame.getStartedAt().toLocalDate())
				.time(fromGame.getStartedAt().toLocalTime())
				.stadiumName(fromGame.getStadium().getName())
				.content(content)
				.build();
	}
}
