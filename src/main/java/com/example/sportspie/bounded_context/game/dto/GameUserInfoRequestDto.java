package com.example.sportspie.bounded_context.game.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameUserInfoRequestDto {
    private Long userId;
    private Long gameId;

    @Builder
    public GameUserInfoRequestDto(Long userId, Long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }
}
