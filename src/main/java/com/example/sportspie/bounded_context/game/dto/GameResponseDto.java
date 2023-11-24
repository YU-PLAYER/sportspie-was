package com.example.sportspie.bounded_context.game.dto;

import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GameResponseDto {
    private Long gameId;
    private Long userId;
    private String title;
    private int maxCapacity;
    private int currentCapacity;
    private LocalDateTime startedAt;
    private Stadium stadium;
    private String content;
    private GameStatus status;
    private GameResult result;

    @Builder
    public GameResponseDto(Long gameId, Long userId, String title, int maxCapacity, int currentCapacity, LocalDateTime startedAt, Stadium stadium, String content, GameStatus status, GameResult result) {
        this.gameId = gameId;
        this.userId = userId;
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
        this.startedAt = startedAt;
        this.stadium = stadium;
        this.content = content;
        this.status = status;
        this.result = result;
    }
}
