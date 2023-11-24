package com.example.sportspie.bounded_context.game.dto;

import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.type.Weather;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class GameListResponseDto {
    private Long gameId;
    private String title;
    private String stadiumName;
    private LocalTime time;
    private Weather weather;
    private GameStatus gameStatus;
    private Integer totalPeople;
    private Integer currentPeople;

    @Builder
    public GameListResponseDto(Long gameId, String title, String stadiumName, LocalTime time, Weather weather, GameStatus gameStatus, Integer totalPeople, Integer currentPeople) {
        this.gameId = gameId;
        this.title = title;
        this.stadiumName = stadiumName;
        this.time = time;
        this.weather = weather;
        this.gameStatus = gameStatus;
        this.totalPeople = totalPeople;
        this.currentPeople = currentPeople;
    }
}
