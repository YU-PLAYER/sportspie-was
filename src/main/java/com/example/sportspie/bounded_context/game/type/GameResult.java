package com.example.sportspie.bounded_context.game.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameResult {
	WIN(0, "승"),
	DRAW(1, "무"),
	LOSE(2, "패");

	private int id;
	private String name;
}
