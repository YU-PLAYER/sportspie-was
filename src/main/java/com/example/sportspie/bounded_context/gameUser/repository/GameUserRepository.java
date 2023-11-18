package com.example.sportspie.bounded_context.gameUser.repository;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.game.type.GameStatus;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameUserRepository extends JpaRepository<GameUser, Long> {

    //경기 + 유저 -> 주키로 사용하여 검색
    GameUser findByJoinUserAndJoinGame(User user, Game game);

    //경기에 참여한 유저 조회
    @Query("SELECT u FROM GameUser u WHERE u.joinGame = :joinGame ORDER BY u.team, u.updatedAt")
    List<GameUser> findByJoinGameOrderByTeam(Game joinGame);

    //참가 신청하려는 경기의 팀 인원 조회
    Long countByJoinGameAndTeam(Game joinGame, GameTeam gameTeam);

    //참여한 경기 조회
    List<GameUser> findByJoinUser(User user);

    //경기 상태 별 경기 조회 - 예정된 경기 목록 조회, 완료된 경기 목록 조회
    @Query("SELECT u FROM GameUser u, Game g WHERE u.joinGame = g and u.joinUser.id = :userId and g.status IN :gameStatuses ORDER BY g.status, g.result nulls first, g.startedAt")
    List<GameUser> findByGameState(Long userId, GameStatus[] gameStatuses);

    //경기 결과가 존재하는 경기 조회 - 전적 조회
    @Query("SELECT u FROM GameUser u, Game g WHERE u.joinGame = g and u.id = :userId and g.result IS NOT NULL")
    List<GameUser> findByGameResult(Long userId);

}
