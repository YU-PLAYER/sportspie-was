package com.example.sportspie.bounded_context.stadium.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weather {
	SUNNY(0, "맑음"),
	CLOUDY(1, "흐림"),
	RAIN(2, "비"),
	SNOW(3, "눈");

	private int id;
	private String name;
}
