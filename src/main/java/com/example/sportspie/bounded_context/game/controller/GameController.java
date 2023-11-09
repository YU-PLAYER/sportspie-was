package com.example.sportspie.bounded_context.game.controller;

import com.example.sportspie.base.api.GameApi;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.dto.GameListResponseDto;
import com.example.sportspie.bounded_context.game.dto.GameResultRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.service.GameService;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GameController implements GameApi {
    private final GameService gameService;

//    로그인 header 값
//    private final JwtProvider jwtProvider;
//    private final UserService userService;

    @Override
    public Game create(GameRequestDto gameRequestDto) {
        return gameService.create(gameRequestDto);
    }

    @Override
    public Page<GameListResponseDto> list(LocalDate startedAt, String sortBy, Pageable pageable) {
        Sort sort = Sort.by("startedAt").ascending();
        if(sortBy.equals("DESC")) sort = Sort.by("startedAt").descending();

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        return gameService.list(startedAt, pageable);
    }
    @Override
    public Page<GameListResponseDto> list(String title, Pageable pageable) {
        return gameService.list(title, pageable);
    }

    @Override
    public Game read(Long id, HttpServletRequest httpServletRequest) {
        // Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(httpServletRequest).substring(7));
        return gameService.read(id);
    }

    @Override
    public Game personConfirm(GameUserRequestDto gameUserRequestDto) {
        return gameService.personConfirm(gameUserRequestDto);
    }

    @Override
    public Game resultConfirm(GameResultRequestDto gameResultRequestDto) {
        return gameService.resultConfirm(gameResultRequestDto);
    }

    @Override
    public Game delete(GameUserRequestDto gameUserRequestDto) {
        return gameService.delete(gameUserRequestDto);
    }
}
