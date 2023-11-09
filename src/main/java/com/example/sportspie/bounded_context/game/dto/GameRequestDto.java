package com.example.sportspie.bounded_context.game.dto;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GameRequestDto {

    private Long authorId;
    private String title;
    private int maxCapacity;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startedAt;
    private Long stadiumId;
    private String content;

    @Builder
    public GameRequestDto(Long authorId, String title, int maxCapacity, LocalDateTime startedAt, Long stadiumId, String content) {
        this.authorId = authorId;
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.startedAt = startedAt;
        this.stadiumId = stadiumId;
        this.content = content;
    }

    public Game toEntity(User author, Stadium stadium){
        return Game.builder()
                .author(author)
                .title(title)
                .maxCapacity(maxCapacity)
                .startedAt(startedAt)
                .stadium(stadium)
                .content(content)
                .status(GameStatus.BEFORE)
                .result(null)
                .currentCapacity(1)
                .build();
    }
}
