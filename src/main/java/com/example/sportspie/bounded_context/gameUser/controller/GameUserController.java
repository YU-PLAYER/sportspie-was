package com.example.sportspie.bounded_context.gameUser.controller;

import com.example.sportspie.base.api.GameUserApi;
import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.gameUser.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinGameResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinUserResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.UserRecordResponseDto;
import com.example.sportspie.bounded_context.gameUser.service.GameUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameUserController implements GameUserApi {
    private final GameUserService gameUserService;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<StateResponse> create(GameUserRequestDto gameUserRequestDto, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameUserService.create(userId, gameUserRequestDto);
    }

    @Override
    public List<JoinGameResponseDto> list(HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameUserService.list(userId);
    }

    @Override
    public List<JoinGameResponseDto> before(HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameUserService.before(userId);
    }

    @Override
    public List<JoinGameResponseDto> after(HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameUserService.after(userId);
    }

    @Override
    public ResponseEntity<StateResponse> delete(GameUserRequestDto gameUserRequestDto, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameUserService.delete(userId, gameUserRequestDto);
    }

    @Override
    public UserRecordResponseDto history(HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameUserService.history(userId);
    }

    @Override
    public UserRecordResponseDto history(Long userId, HttpServletRequest request) {
        return gameUserService.history(userId);
    }

    @Override
    public List<JoinUserResponseDto> joinList(Long gameId, HttpServletRequest request) {
        return gameUserService.joinMemberList(gameId);
    }
}
