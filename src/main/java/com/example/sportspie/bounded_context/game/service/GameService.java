package com.example.sportspie.bounded_context.game.service;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.dto.GameResultRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameUserInfoRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.repository.GameRepository;
import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Game read(Long gameId){
        return gameRepository.findById(gameId).orElseThrow(()-> new IllegalArgumentException("해당 경기가 없습니다. id=" + gameId));
    }

    @Transactional
    public Game personConfirm(GameUserInfoRequestDto request){
        Game game = read(request.getGameId());
        Long userId = request.getUserId();

        if(!game.isAuthor(userId)) new IllegalArgumentException("작성자만 경기 인원을 확정할 수 있습니다.");
        else if(!game.isSatisfiedCapacity()) new IllegalArgumentException("해당 경기의 양 팀 인원 수가 맞지 않습니다.");
        else game.setStatus(GameStatus.PROGRESS);
        return game;
    }

    @Transactional
    public Game resultConfirm(GameResultRequestDto request){
        Long userId = request.getUserId();
        GameResult gameResult = request.getGameResult();
        Game game = read(request.getGameId());

        if(!game.isAuthor(userId)) new IllegalArgumentException("작성자만 경기 결과를 확정할 수 있습니다.");
        else if(!game.isSatisfiedResult()) new IllegalArgumentException("경기 인원이 확정되었고 경기 시작시간이 지난 경기만 결과를 확정할 수 있습니다.");
        else {
            game.setResult(gameResult);
            game.setStatus(GameStatus.AFTER);
        }
        return game;
    }

    public Game delete(GameUserInfoRequestDto request){
        Game game = read(request.getGameId());
        Long userId = request.getUserId();

        if(!game.isAuthor(userId)) new IllegalArgumentException("작성자만 경기를 삭제할 수 있습니다.");
        if(!game.isSatisfiedDelete()) new IllegalArgumentException("경기 인원이 확정되지 않았고 경기 시작 시간 2시간 전인 경기만 삭제할 수 있습니다.");
        else gameRepository.delete(game);
        return game;
    }
}
