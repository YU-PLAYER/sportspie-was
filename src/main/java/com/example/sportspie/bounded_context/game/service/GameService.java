package com.example.sportspie.bounded_context.game.service;

import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.dto.*;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.repository.GameRepository;
import com.example.sportspie.bounded_context.game.type.GameResult;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import com.example.sportspie.bounded_context.gameUser.repository.GameUserRepository;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import com.example.sportspie.bounded_context.notification.service.NotificationService;
import com.example.sportspie.bounded_context.notification.type.NotificationType;
import com.example.sportspie.bounded_context.stadium.entity.Stadium;
import com.example.sportspie.bounded_context.stadium.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final NotificationService notificationService;
    private final GameUserRepository gameUserRepository;

    /**
     * 경기 생성
     * @param userId
     * @param gameRequestDto
     * @return
     */
    @Transactional
    public ResponseEntity<StateResponse> create(Long userId, GameRequestDto gameRequestDto){
        User author = userService.read(userId);
        Stadium stadium = stadiumService.read(gameRequestDto.getStadiumId().intValue());

        //로직
        Game game = gameRepository.save(gameRequestDto.toEntity(author, stadium));
        gameUserRepository.save(GameUser.builder()
                .joinGame(game)
                .joinUser(author)
                .team(GameTeam.HOME).build());

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("경기 생성을 성공적으로 완료했습니다.").build());
    }

    /**
     * 날짜 별 경기 목록(오름차순/내림차순/검색)
     * @param startedAt
     * @param title
     * @param pageable
     * @return
     */
    public Page<GameListResponseDto> list(LocalDate startedAt, String title, Pageable pageable){
        LocalDateTime startOfDay = startedAt.atStartOfDay();
        LocalDateTime endOfDay = startedAt.atTime(LocalTime.MAX);
        Page<Game> gamePage;
        if(title.isEmpty()) gamePage = gameRepository.findByStartedAtBetween(startOfDay, endOfDay, pageable);
        else gamePage = gameRepository.findByStartedAtBetweenAndTitleContaining(startOfDay, endOfDay, title, pageable);
        return gamePage.map(game -> game.toListDto());
    }

    /**
     * 경기 상세 보기
     * @param gameId
     * @return Game [미정]
     */
    public GameResponseDto detail(Long gameId){
        return gameRepository.findById(gameId).map(game-> game.toDto()).orElseThrow(()-> new IllegalArgumentException("해당 경기가 없습니다. id=" + gameId));
    }

    public Game read(Long gameId){
        return gameRepository.findById(gameId).orElseThrow(()-> new IllegalArgumentException("해당 경기가 없습니다. id=" + gameId));
    }


    /**
     * 경기 인원 확정
     * @param userId
     * @param gameId
     * @return
     */
    @Transactional
    public ResponseEntity<StateResponse> personConfirm(Long userId, Long gameId){
        Game game = read(gameId);
        User user = userService.read(userId);

        if(!game.isAuthor(user)) throw new IllegalArgumentException("작성자만 경기 인원을 확정할 수 있습니다.");
        if(!game.isSatisfiedCapacity()) throw new IllegalArgumentException("해당 경기의 양 팀 인원 수가 맞지 않습니다.");

        //로직
        game.setStatus(GameStatus.PROGRESS);
        notificationService.create(game, NotificationType.GAME_CONFIRMED);

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("경기 인원 확정을 성공적으로 완료했습니다.").build());
    }

    /**
     * 경기 결과 확정
     * @param userId
     * @param gameResultRequestDto
     * @return
     */
    @Transactional
    public ResponseEntity<StateResponse> resultConfirm(Long userId, GameResultRequestDto gameResultRequestDto){
        User user = userService.read(userId);
        Game game = read(gameResultRequestDto.getGameId());
        GameResult gameResult = gameResultRequestDto.getGameResult();

        if(!game.isAuthor(user)) throw new IllegalArgumentException("작성자만 경기 결과를 확정할 수 있습니다.");
        if(!game.isSatisfiedResult()) throw new IllegalArgumentException("경기 인원이 확정되었고 경기 시작시간이 지난 경기만 결과를 확정할 수 있습니다.");

        //로직
        game.setResult(gameResult);
        game.setStatus(GameStatus.AFTER);
        notificationService.create(game, NotificationType.RESULTS_CONFIRMED);

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("경기 결과 확정을 성공적으로 완료했습니다.").build());
    }

    /**
     * 경기 삭제
     * @param userId
     * @param gameId
     * @return
     */
    @Transactional
    public ResponseEntity<StateResponse> delete(Long userId, Long gameId){
        User user = userService.read(userId);
        Game game = read(gameId);
        List<GameUser> gameUsers = gameUserRepository.findGameUserByJoinGame(game);
        gameUserRepository.deleteAll(gameUsers);

        if(!game.isAuthor(user)) throw new IllegalArgumentException("작성자만 경기를 삭제할 수 있습니다.");
        if(!game.isSatisfiedDelete()) throw new IllegalArgumentException("경기 인원이 확정되지 않았고 경기 시작 시간 2시간 전인 경기만 삭제할 수 있습니다.");

        gameRepository.delete(game);

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("경기 삭제를 성공적으로 완료했습니다.").build());
    }
}
