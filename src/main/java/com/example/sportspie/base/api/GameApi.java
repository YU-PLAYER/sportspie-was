package com.example.sportspie.base.api;

import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.bounded_context.game.dto.GameListResponseDto;
import com.example.sportspie.bounded_context.game.dto.GameResultRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/api/game")
@Tag(name = "Game", description = "Game 관련 API")
public interface GameApi {

    @PostMapping("")
    @Operation(summary = "Game 생성 메서드", description = "로그인한 사용자가 경기를 생성하기 위한 메서드 입니다.")
    ResponseEntity<StateResponse> create(@RequestBody GameRequestDto gameRequestDto);

    @GetMapping("/{startedAt}")
    @Operation(summary = "Game 날짜별 목록 조회 메서드", description = "사용자가 시스템의 모든 경기 목록을 날짜별로 조회하기 위한 메서드 입니다.")
    Page<GameListResponseDto> list(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable LocalDate startedAt,
                                   @RequestParam(name = "sortBy", defaultValue = "ASC")String sort,
                                   @PageableDefault(page = 0, size = 10) Pageable pageable);
    @GetMapping("/search")
    @Operation(summary = "Game 제목 검색 메서드", description = "사용자가 시스템의 모든 경기 목록에 대해 제목으로 검색하기 위한 메서드 입니다.")
    Page<GameListResponseDto> list(@RequestParam(name = "title", defaultValue = "")String title,
                                   @PageableDefault(page = 0, size = 10) Pageable pageable);

    @GetMapping("/detail/{id}")
    @Operation(summary = "Game 상세 조회 메서드", description = "로그인한 사용자가 경기 정보를 상세 조회하기 위한 메서드 입니다.")
    Game read(@PathVariable Long id, HttpServletRequest request);

    @PatchMapping("/progress")
    @Operation(summary = "Game 인원 확정 메서드", description = "경기 인원 모집글 작성자가 경기 인원 확정 조건을 만족할 때 경기 인원을 확정하기 위한 메서드 입니다.")
    ResponseEntity<StateResponse> personConfirm(@RequestParam GameUserRequestDto gameUserRequestDto);

    @PatchMapping("/after")
    @Operation(summary = "Game 결과 확정 메서드", description = "경기 인원 모집글 작성자가 경기 결과 확정 조건을 만족할 때 경기 결과를 확정하기 위한 메서드 입니다.")
    ResponseEntity<StateResponse> resultConfirm(@RequestBody GameResultRequestDto gameResultRequestDto);

    @PostMapping("/delete")
    @Operation(summary = "Game 삭제 메서드", description = "경기 인원 모집글 작성자가 경기 삭제 조건을 만족할 때 경기를 삭제하기 위한 메서드 입니다.")
    ResponseEntity<StateResponse> delete(@RequestBody GameUserRequestDto gameUserRequestDto);
}
