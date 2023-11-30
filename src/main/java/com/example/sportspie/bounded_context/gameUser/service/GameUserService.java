package com.example.sportspie.bounded_context.gameUser.service;

import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.service.GameService;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.gameUser.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinGameResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinUserResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.UserRecordResponseDto;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import com.example.sportspie.bounded_context.gameUser.repository.GameUserRepository;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;


@Service
@RequiredArgsConstructor
public class GameUserService {
    private final GameUserRepository gameUserRepository;
    private final UserService userService;
    private final GameService gameService;

    public GameUser read(User user, Game game){
        return gameUserRepository.findByJoinUserAndJoinGame(user, game);
    }

    /**
     * 경기 참가 신청
     * @param gameUserRequestDto
     * @return
     */
    @Transactional
    public ResponseEntity<StateResponse> create(Long userId, GameUserRequestDto gameUserRequestDto){
        User user = userService.read(userId);
        Game game = gameService.read(gameUserRequestDto.getGameId());
        GameUser gameUser = read(user, game);

        //참가 조건
        if(gameUser!=null) throw new IllegalStateException("해당 경기에 참가 중입니다. 참가 취소 후 참가 할 수 있습니다.");
        if(!game.isSatisfiedJoin()) throw new IllegalStateException("경기 전체 인원의 최대 인원에 도달하여 참가할 수 없습니다."); //isSatisfiedJoin
        if(isTeamFull(game, gameUserRequestDto.getGameTeam())) throw new IllegalStateException("경기 팀 인원의 최대 인원에 도달하여 참가할 수 없습니다.");

        //참가 로직
        gameUser = gameUserRepository.save(gameUserRequestDto.toEntity(user, game));
        game.increaseCurrentCapacity();

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("\" " + game.getTitle() + " \" 경기 참여를 성공적으로 완료했습니다.").build());
    }

    /**
     * 경기 참가 취소
     * @param gameUserRequestDto
     * @return
     */
    public ResponseEntity<StateResponse> delete(Long userId, GameUserRequestDto gameUserRequestDto){
        User user = userService.read(userId);
        Game game = gameService.read(gameUserRequestDto.getGameId());
        GameUser gameUser = read(user, game);

        //취소 조건
        if(gameUser == null) throw new IllegalStateException("해당 경기에 참가한 내역이 없습니다. 경기 참가 후 취소 할 수 있습니다.");
        if(!game.getStatus().equals(GameStatus.BEFORE)) throw new IllegalStateException("완료된 경기나, 인원이 확정된 경기는 참가 취소할 수 없습니다.");
        if(game.isAuthor(user)) throw new IllegalStateException("해당 경기의 작성자는 참가 취소할 수 없습니다.");

        //취소 로직
        gameUserRepository.delete(gameUser);
        game.decreaseCurrentCapacity();

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("\" " + game.getTitle() + " \" 경기 참여 취소를 성공적으로 완료했습니다.").build());
    }


    /**
     * 전체 참가 경기 목록 조회
     * @param userId
     * @return
     */
    public List<JoinGameResponseDto> list(Long userId){
        User user = userService.read(userId);
        return gameUserRepository.findByJoinUser(user).stream()
                .map(gameUser -> gameUser.toGameDto())
                .collect(Collectors.toList());
    }

    /**
     * 참가한 경기 중 예정된 경기 목록 조회
     * @param userId
     * @return
     */
    public List<JoinGameResponseDto> before(Long userId){
        return gameUserRepository.findByBeforeGame(userId, LocalDateTime.now())
                .stream().map(gameUser -> gameUser.toGameDto())
                .collect(Collectors.toList());
    }

    /**
     * 참가한 경기 중 완료된 경기 목록 조회
     * @param userId
     * @return
     */
    public List<JoinGameResponseDto> after(Long userId){
        return gameUserRepository.findByAfterGame(userId, LocalDateTime.now())
                .stream().map(gameUser -> gameUser.toGameDto())
                .collect(Collectors.toList());
    }


    /**
     * 경기 전적 조회
     * @param userId
     * @return
     */
    public UserRecordResponseDto history(Long userId){
        ArrayList<Integer> recordList = new ArrayList<>();
        int[] history = new int[3];
        gameUserRepository.findByGameResult(userId).stream()
                .forEach(gameUser -> recordList.add(gameUser.calcGameResult(gameUser.getJoinGame().getResult()).getId()));
        recordList.stream().forEach(result -> history[result]++);

        return UserRecordResponseDto.builder()
                .win(history[0])
                .draw(history[1])
                .lose(history[2])
                .recent10(recordList.subList(0, min(recordList.size(), 9)))
                .build();
    }

    /**
     * 경기에 참가 신청한 유저 목록
     * @param gameId
     * @return
     */
    public List<JoinUserResponseDto> joinMemberList(Long gameId){
        Game game = gameService.read(gameId);
        return gameUserRepository.findByJoinGameOrderByTeam(game).stream()
                .map(gameUser -> gameUser.toUserDto())
                .collect(Collectors.toList());
    }

    /**
     * 팀 참가 조건
     */
    public Boolean isTeamFull(Game game, GameTeam gameTeam){
        return gameUserRepository.countByJoinGameAndTeam(game, gameTeam) > game.getMaxCapacity() / 2;
    }

}
