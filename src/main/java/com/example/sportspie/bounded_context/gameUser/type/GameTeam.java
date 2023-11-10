package com.example.sportspie.bounded_context.gameUser.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameTeam {
    HOME(0, "작성자와 같은 팀"),
    AWAY(1, "작성자와 다른 팀");

    private int id;
    private String name;
}
