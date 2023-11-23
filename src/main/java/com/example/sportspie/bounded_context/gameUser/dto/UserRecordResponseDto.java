package com.example.sportspie.bounded_context.gameUser.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserRecordResponseDto {
    private int win;
    private int draw;
    private int lose;
    private List<Integer> recent10;

    @Builder
    public UserRecordResponseDto(int win, int draw, int lose, List<Integer> recent10) {
        this.win = win;
        this.draw = draw;
        this.lose = lose;
        this.recent10 = recent10;
    }
}
