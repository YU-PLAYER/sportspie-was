package com.example.sportspie.bounded_context.game.dto;

import com.example.sportspie.bounded_context.game.type.GameResult;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameResultRequestDto {
    private Long gameId;
    private GameResult gameResult;

    @Builder
    public GameResultRequestDto(Long userId, Long gameId, GameResult gameResult) {
        this.gameId = gameId;
        this.gameResult = gameResult;
    }
}
