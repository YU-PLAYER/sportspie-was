package com.example.sportspie.bounded_context.gameUser.dto;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GameUserRequestDto {
    private Long userId;
    private Long gameId;
    private GameTeam gameTeam;

    @Builder
    public GameUserRequestDto(Long userId, Long gameId, GameTeam gameTeam) {
        this.userId = userId;
        this.gameId = gameId;
        this.gameTeam = gameTeam;
    }

    public GameUser toEntity(User user, Game game){
        return GameUser.builder()
                .joinUser(user)
                .joinGame(game)
                .team(gameTeam).build();
    }
}
