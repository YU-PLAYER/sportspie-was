package com.example.sportspie.base.api;

import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.bounded_context.gameUser.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinGameResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinUserResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.UserRecordResponseDto;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/gameUser")
@Tag(name = "GameUser", description = "GameUser 관련 API")
public interface GameUserApi {

    @PostMapping("")
    @Operation(summary = "GameUser 생성 메서드", description = "사용자가 경기에 참여하기 위한 위한 메서드입니다.")
    ResponseEntity<StateResponse> create(@RequestBody GameUserRequestDto gameUserRequestDto);

    @GetMapping("/list")
    @Operation(summary = "GameUser 모든 경기 목록 조회 메서드", description = "사용자가 참여한 모든 경기 목록을 조회하기 위한 메서드 입니다.")
    List<JoinGameResponseDto> list(HttpServletRequest request);

    @GetMapping("/before")
    @Operation(summary = "GameUser 예정 경기 목록 조회 메서드", description = "사용자가 참여한 모든 경기 중 예정된 경기 목록을 조회하기 위한 메서드 입니다.")
    List<JoinGameResponseDto> before(HttpServletRequest request);

    @GetMapping("/after")
    @Operation(summary = "GameUser 완료 경기 목록 조회 메서드", description = "사용자가 참여한 모든 경기 중 완료된 경기 목록을 조회하기 위한 메서드 입니다.")
    List<JoinGameResponseDto> after(HttpServletRequest request);

    @PostMapping("/delete")
    @Operation(summary = "GameUser 삭제 메서드", description = "사용자가 경기 참여를 취소하기 위한 메서드 입니다.")
    ResponseEntity<StateResponse> delete(@RequestBody GameUserRequestDto gameUserRequestDto);

    @GetMapping("/history")
    @Operation(summary = "GameUser 전적 조회 메서드", description = "사용자의 전적을 조회하기 위한 메서드 입니다.")
    UserRecordResponseDto history(HttpServletRequest request);

    @GetMapping("/join/{gameId}")
    @Operation(summary = "GameUser 참여 인원 조회 메서드", description = "경기에 참가 신청을 한 모든 유저를 조회하기 위한 메서드 입니다.")
    List<JoinUserResponseDto> joinList(@PathVariable Long gameId, HttpServletRequest request);

}
