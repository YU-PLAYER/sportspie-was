package com.example.sportspie.bounded_context.game.service;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.dto.GameUserInfoRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.repository.GameRepository;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService{
    private final GameRepository gameRepository;
    private final UserService userService;
    private final StadiumService stadiumService;

    public Game create(GameRequestDto gameRequestDto){
        User author = userService.read(gameRequestDto.getAuthorId());
        Stadium stadium = stadiumService.read(gameRequestDto.getStadiumId()); //stadiumService 메서드 사용
        return gameRepository.save(gameRequestDto.toEntity(author, stadium));
    }

    public List<Game> list(LocalDate startAt){
        LocalDateTime startOfDay = startAt.atStartOfDay();
        LocalDateTime endOfDay = startAt.atTime(LocalTime.MAX);
        return gameRepository.findByStartedAtBetween(startOfDay, endOfDay);
    }

    public Game read(GameUserInfoRequestDto gameUserInfoRequestDto){
        userService.read(gameUserInfoRequestDto.getUserId());
        return gameRepository.findById(gameUserInfoRequestDto.getGameId()).orElseThrow(() -> new IllegalArgumentException("해당 게임이 없습니다. id=" + gameUserInfoRequestDto.getGameId()));
    }
}
