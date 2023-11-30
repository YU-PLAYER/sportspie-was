package com.example.sportspie.bounded_context.gameUser.dto;

import com.example.sportspie.bounded_context.auth.type.Position;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class JoinUserResponseDto {

    private Long userId;
    private String name;
    private String imgUrl;
    private Boolean goalkeeper;
    private Boolean defender;
    private Boolean midfielder;
    private Boolean attacker;
    private GameTeam gameTeam;
    private LocalDateTime updatedAt;

    @Builder
    public JoinUserResponseDto(Long userId, String name, String imgUrl, Boolean goalkeeper, Boolean defender, Boolean midfielder, Boolean attacker, GameTeam gameTeam, LocalDateTime updatedAt) {
        this.userId = userId;
        this.name = name;
        this.imgUrl = imgUrl;
        this.goalkeeper = goalkeeper;
        this.defender = defender;
        this.midfielder = midfielder;
        this.attacker = attacker;
        this.gameTeam = gameTeam;
        this.updatedAt = updatedAt;
    }
}
