package com.example.sportspie.bounded_context.game.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameUserRequestDto {
    private Long userId;
    private Long gameId;

    public GameUserRequestDto(Long userId, Long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }
}
