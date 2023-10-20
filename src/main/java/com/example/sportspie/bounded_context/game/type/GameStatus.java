package com.example.sportspie.bounded_context.game.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {
	BEFORE(0, "경기 예정"),
	PROGRESS(1, "경기 확정"),
	AFTER(2, "경기 종료");

	private int id;
	private String name;
}
