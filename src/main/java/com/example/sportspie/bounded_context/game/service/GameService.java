package com.example.sportspie.bounded_context.game.service;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.dto.GameListResponseDto;
import com.example.sportspie.bounded_context.game.dto.GameResultRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.game.dto.GameRequestDto;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.repository.GameRepository;
import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.service.StadiumService;
import com.example.sportspie.bounded_context.stadium.type.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class GameService{
    private final GameRepository gameRepository;
    private final UserService userService;
//    private final StadiumService stadiumService;


    /**
     * 경기 생성
     * 생성자는 자동으로 참가되어야 한다.
     * 반환 값에 대한 논의
     */
    public Game create(GameRequestDto gameRequestDto){
        User author = userService.read(gameRequestDto.getAuthorId());
        //Stadium stadium = stadiumService.read(gameRequestDto.getStadiumId());
        Stadium stadium = new Stadium();
        Game game = gameRepository.save(gameRequestDto.toEntity(author, stadium));
        return game;
    }

    /**
     * 날짜 별 경기 목록(경기 시작 시간 오름차순/내림차순)
     */
    public Page<GameListResponseDto> list(LocalDate startedAt, Pageable pageable){
        LocalDateTime startOfDay = startedAt.atStartOfDay();
        LocalDateTime endOfDay = startedAt.atTime(LocalTime.MAX);
        Page<Game> gamePage = gameRepository.findByStartedAtBetween(startOfDay, endOfDay, pageable);
        return gamePage.map(game -> GameListResponseDto.builder()
                    .gameId(game.getId())
                    .gameStatus(game.getStatus())
                    .title(game.getTitle())
                    .time(game.getStartedAt().toLocalTime())
                    .stadiumName(game.getStadium().getName())
                    .totalPeople(game.getMaxCapacity())
                    .currentPeople(game.getCurrentCapacity()).build());
    }

    /**
     * 경기 검색
     */
    public Page<GameListResponseDto> list(String title, Pageable pageable){
        Page<Game> gamePage = gameRepository.findByTitleContaining(title, pageable);
        return gamePage.map(game -> GameListResponseDto.builder()
                .gameId(game.getId())
                .gameStatus(game.getStatus())
                .title(game.getTitle())
                .time(game.getStartedAt().toLocalTime())
                .stadiumName(game.getStadium().getName())
                .totalPeople(game.getMaxCapacity())
                .currentPeople(game.getCurrentCapacity()).build());
    }

    /**
     * 경기 상세보기
     */
    public Game read(Long gameId){
        return gameRepository.findById(gameId).orElseThrow(()-> new IllegalArgumentException("해당 경기가 없습니다. id=" + gameId));
    }

    /**
     * 경기 인원 확정
     */
    @Transactional
    public Game personConfirm(GameUserRequestDto request){
        Game game = read(request.getGameId());
        Long userId = request.getUserId();

        if(!game.isAuthor(userId)) new IllegalArgumentException("작성자만 경기 인원을 확정할 수 있습니다.");
        else if(!game.isSatisfiedCapacity()) new IllegalArgumentException("해당 경기의 양 팀 인원 수가 맞지 않습니다.");
        else game.setStatus(GameStatus.PROGRESS);
        return game;
    }

    /**
     * 경기 결과 확정
     */
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

    /**
     * 경기 삭제
     */
    public Game delete(GameUserRequestDto request){
        Game game = read(request.getGameId());
        Long userId = request.getUserId();

        if(!game.isAuthor(userId)) new IllegalArgumentException("작성자만 경기를 삭제할 수 있습니다.");
        if(!game.isSatisfiedDelete()) new IllegalArgumentException("경기 인원이 확정되지 않았고 경기 시작 시간 2시간 전인 경기만 삭제할 수 있습니다.");
        else gameRepository.delete(game);
        return game;
    }
}
