package com.example.sportspie.bounded_context.gameUser.entitiy;

import com.example.sportspie.base.entity.BaseTimeEntity;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.type.Position;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.gameUser.dto.JoinGameResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinUserResponseDto;
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

    public GameResult calcGameResult(GameResult gameResult) {
        if (gameResult == null) return null;
        if (team.equals(GameTeam.HOME) || gameResult.equals(GameResult.DRAW)) return gameResult;
        return gameResult.equals(GameResult.WIN) ? GameResult.LOSE : GameResult.WIN;
    }

    /**
     * 참여 경기 정보
     * title, date, time, numJoin, maxJoin, gameState, gameResult
     */
    public JoinGameResponseDto toGameDto() {
        Game game = joinGame;
        return JoinGameResponseDto.builder()
                .title(game.getTitle())
                .date(game.getStartedAt().toLocalDate())
                .time(game.getStartedAt().toLocalTime())
                .numJoin(game.getCurrentCapacity())
                .maxJoin(game.getMaxCapacity())
                .gameStatus(game.getStatus())
                .gameResult(calcGameResult(game.getResult()))
                .build();
    }

    /**
     * 참여 인원 정보
     * name, imgUrl, position, team
     * id, updatedAt
     */
    public JoinUserResponseDto toUserDto(){
        User user = joinUser;
        return JoinUserResponseDto.builder()
                .userId(user.getId()) //redirection user profile
                .name(user.getNickname())
                .imgUrl(user.getImageUrl())
                .goalkeeper(user.getGoalkeeper())
                .defender(user.getDefender())
                .midfielder(user.getMidfielder())
                .attacker(user.getAttacker())
                .gameTeam(team)
                .updatedAt(getUpdatedAt()) //order by
                .build();
    }
}
