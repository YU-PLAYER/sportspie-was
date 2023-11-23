package com.example.sportspie.bounded_context.gameUser.dto;

import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@NoArgsConstructor
public class JoinGameResponseDto {
    private String title;
    private LocalDate date;
    private LocalTime time;
    private Integer numJoin;
    private Integer maxJoin;
    private GameStatus gameStatus;
    private GameResult gameResult;

    @Builder
    public JoinGameResponseDto(String title, LocalDate date, LocalTime time, Integer numJoin, Integer maxJoin, GameStatus gameStatus, GameResult gameResult) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.numJoin = numJoin;
        this.maxJoin = maxJoin;
        this.gameStatus = gameStatus;
        this.gameResult = gameResult;
    }
}
