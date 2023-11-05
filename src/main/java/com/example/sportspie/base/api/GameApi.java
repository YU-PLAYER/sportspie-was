package com.example.sportspie.base.api;

import com.example.sportspie.bounded_context.game.dto.GameUserInfoRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api/game")
@Tag(name = "Game", description = "Game 관련 API")
public interface GameApi {

    @PostMapping("")
    @Operation(summary = "Game 생성 메서드", description = "로그인한 사용자가 게임을 생성하기 위한 메서드 입니다.")
    Game create(@RequestBody GameRequestDto gameRequestDto);

    @GetMapping("/{startedAt}")
    @Operation(summary = "Game 목록 조회 메서드", description = "사용자가 게임 목록을 조회하기 위한 메서드 입니다.")
    List<Game> list(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate startedAt);

    @PostMapping("/detail")
    @Operation(summary = "Game 상세 조회 메서드", description = "로그인한 사용자가 게임 정보를 상세 조회하기 위한 메서드 입니다.")
    Game read(@RequestBody GameUserInfoRequestDto gameUserInfoRequestDto);
}
