package com.example.sportspie.bounded_context.game.controller;

import com.example.sportspie.base.api.GameApi;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.dto.GameResultRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameUserInfoRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.service.GameService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController implements GameApi {
    private final GameService gameService;

    @Override
    public Game create(GameRequestDto gameRequestDto) {
        return gameService.create(gameRequestDto);
    }

    @Override
    public List<Game> list(LocalDate startedAt) {
        return gameService.list(startedAt);
    }
    @Override
    public Game read(Long id, HttpServletRequest httpServletRequest) {
        return gameService.read(id);
    }

    @Override
    public Game personConfirm(GameUserInfoRequestDto gameUserInfoRequestDto) {
        return gameService.personConfirm(gameUserInfoRequestDto);
    }

    @Override
    public Game resultConfirm(GameResultRequestDto gameResultRequestDto) {
        return gameService.resultConfirm(gameResultRequestDto);
    }

    @Override
    public Game delete(GameUserInfoRequestDto gameUserInfoRequestDto) {
        return gameService.delete(gameUserInfoRequestDto);
    }
}
