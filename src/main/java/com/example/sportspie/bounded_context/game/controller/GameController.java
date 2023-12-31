package com.example.sportspie.bounded_context.game.controller;

import com.example.sportspie.base.api.GameApi;
import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.game.dto.*;
import com.example.sportspie.bounded_context.game.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class GameController implements GameApi {
    private final GameService gameService;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<StateResponse> create(GameRequestDto gameRequestDto, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameService.create(userId, gameRequestDto);
    }

    @Override
    public Page<GameListResponseDto> list(LocalDate startedAt, String sortBy, String title, Pageable pageable) {
        Sort sort = Sort.by("startedAt").ascending();
        if(sortBy.equals("DESC")) sort = Sort.by("startedAt").descending();

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return gameService.list(startedAt, title, pageable);
    }

    @Override
    public GameResponseDto detail(Long gameId, HttpServletRequest request) {
        return gameService.detail(gameId);
    }

    @Override
    public ResponseEntity<StateResponse> personConfirm(Long gameId, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameService.personConfirm(userId, gameId);
    }

    @Override
    public ResponseEntity<StateResponse> resultConfirm(GameResultRequestDto gameResultRequestDto, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameService.resultConfirm(userId, gameResultRequestDto);
    }

    @Override
    public ResponseEntity<StateResponse> delete(Long gameId, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return gameService.delete(userId, gameId);
    }
}
