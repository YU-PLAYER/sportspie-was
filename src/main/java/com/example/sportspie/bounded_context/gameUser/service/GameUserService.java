package com.example.sportspie.bounded_context.gameUser.service;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.service.GameService;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.gameUser.dto.GameUserRequestDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinGameResponseDto;
import com.example.sportspie.bounded_context.gameUser.dto.JoinUserResponseDto;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import com.example.sportspie.bounded_context.gameUser.repository.GameUserRepository;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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
     * 예외
     * 1, 참가 내역이 있는 경우 업데이트
     * 2. 없는 경우 생성
     * 로직
     * 1. 레코드 저장
     * 1. 게임 현재 인원 증가
     * @param gameUserRequestDto
     * @return GameUser / url
     */
    @Transactional
    public GameUser create(GameUserRequestDto gameUserRequestDto){
        User user = userService.read(gameUserRequestDto.getUserId());
        Game game = gameService.read(gameUserRequestDto.getGameId());
        GameUser gameUser = read(user, game);

        //참가 조건
        if(!game.isSatisfiedJoin()) throw new IllegalStateException("경기 인원이 최대 인원에 도달하여 참가할 수 없습니다.");
        if(isTeamFull(game, gameUserRequestDto.getGameTeam())) throw new IllegalStateException("팀 인원이 최대 인원에 도달하여 참가할 수 없습니다.");

        //참가 로직
        if(gameUser!=null) gameUser.setTeam(gameUserRequestDto.getGameTeam());
        else {
            gameUser = gameUserRepository.save(gameUserRequestDto.toEntity(user, game));
            game.increaseCurrentCapacity();
        }

        return gameUser;
    }


    /**
     * 경기 참가 취소
     * 예외
     * 1. 참가 내역이 없는 경우
     * 로직
     * 1. 레코드 삭제
     * 2. 게임 현재 인원 감소
     * @param gameUserRequestDto
     * @return GameUser / url
     */
    public GameUser delete(GameUserRequestDto gameUserRequestDto){
        User user = userService.read(gameUserRequestDto.getUserId());
        Game game = gameService.read(gameUserRequestDto.getGameId());
        GameUser gameUser = read(user, game);

        //취소 조건
        if(gameUser == null) throw new IllegalStateException("경기에 참가한 내역이 없습니다.");

        //취소 로직
        gameUserRepository.delete(gameUser);
        game.decreaseCurrentCapacity();

        return gameUser;
    }

    /**
     * 전체 참가 경기 목록 조회
     * 정렬 (예정/종료/경기시작시간)
     * @param userId
     * @return JoinGameResponseDto
     */
    public List<JoinGameResponseDto> list(Long userId){
        User user = userService.read(userId);
        return gameUserRepository.findByGameState(userId, new GameStatus[]{GameStatus.BEFORE, GameStatus.PROGRESS, GameStatus.AFTER}).stream()
                .map(gameUser -> gameUser.toGameDto())
                .collect(Collectors.toList());
    }

    /**
     * 참가한 경기 중 예정된 경기 목록 조회 #정렬 미해결
     * 정렬 (인원미확정/인원확정/경기시작시간)
     * @param userId
     * @return JoinGameResponseDto
     */
    public List<JoinGameResponseDto> before(Long userId){
        return gameUserRepository.findByGameState(userId, new GameStatus[]{GameStatus.BEFORE, GameStatus.PROGRESS})
                .stream().map(gameUser -> gameUser.toGameDto())
                .collect(Collectors.toList());
    }

    /**
     * 참가한 경기 중 완료된 경기 목록 조회 #정렬 미해결
     * 정렬 (결과미확정/결과확정/경기시작시간)
     * @param userId
     * @return JoinGameResponseDto
     */
    public List<JoinGameResponseDto> after(Long userId){
        return gameUserRepository.findByGameState(userId, new GameStatus[]{GameStatus.AFTER})
                .stream().map(gameUser -> gameUser.toGameDto())
                .collect(Collectors.toList());
    }

    /**
     * 경기 전적 조회
     * @param userId
     * @return int[]
     */
    public int[] history(Long userId){
        int[] history = new int[3];
        gameUserRepository.findByGameResult(userId).stream()
                .forEach(gameUser -> history[gameUser.calcGameResult(gameUser.getJoinGame().getResult()).getId()]++);
        return history;
    }

    /**
     * 경기에 참가 신청한 유저 목록
     * 정렬(팀/업데이트시간)
     * @param gameId
     * @return JoinUserResponseDto
     */
    public List<JoinUserResponseDto> joinMemberList(Long gameId){
        Game game = gameService.read(gameId);
        return gameUserRepository.findByJoinGameOrderByTeam(game).stream()
                .map(gameUser -> gameUser.toUserDto())
                .collect(Collectors.toList());
    }

    //팀 참가 조건
    public Boolean isTeamFull(Game game, GameTeam gameTeam){
        return gameUserRepository.countByJoinGameAndTeam(game, gameTeam) > game.getMaxCapacity() / 2;
    }

}