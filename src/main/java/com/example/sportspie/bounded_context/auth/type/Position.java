package com.example.sportspie.bounded_context.auth.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Position {
	GK(0, "골키퍼"),
	DF(1, "수비수"),
	MF(2, "미드필더"),
	FW(3, "공격수");

	private int id;
	private String name;
}
