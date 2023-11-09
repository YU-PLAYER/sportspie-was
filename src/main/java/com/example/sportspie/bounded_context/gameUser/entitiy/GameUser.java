package com.example.sportspie.bounded_context.gameUser.entitiy;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GameUser extends BaseTimeEntity {
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private User joinUser;

    @JoinColumn(name = "game_id", nullable = false)
    @ManyToOne
    private Game joinGame;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private GameTeam team;

    @Builder
    public GameUser(User joinUser, Game joinGame, GameTeam team) {
        this.joinUser = joinUser;
        this.joinGame = joinGame;
        this.team = team;
    }
}
