package com.example.sportspie.bounded_context.notification.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum NotificationType {
	GAME_CONFIRMED(0, "경기 확정"),
	RESULTS_CONFIRMED(1, "경기 결과 확정"),
	DATE_IMMINENT(2, "경기 날짜 임박"),
	REPORTED(3, "신고");

	private int id;
	private String name;
}
