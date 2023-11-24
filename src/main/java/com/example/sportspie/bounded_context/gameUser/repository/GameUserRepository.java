package com.example.sportspie.bounded_context.gameUser.repository;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.gameUser.entitiy.GameUser;
import com.example.sportspie.bounded_context.gameUser.type.GameTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 1. 속성 타입 통일(id/user.getId)
 * 2. 비슷한 질의 병합
 * 3. 반환 타입 고민
 */

public interface GameUserRepository extends JpaRepository<GameUser, Long> {

    //read : 경기 + 유저 -> 주키로 사용하여 검색
    GameUser findByJoinUserAndJoinGame(User user, Game game);

    //joinMemberList : 경기에 참여한 유저 조회 GameUser
    @Query("SELECT u FROM GameUser u WHERE u.joinGame = :joinGame ORDER BY u.team, u.updatedAt")
    List<GameUser> findByJoinGameOrderByTeam(Game joinGame);

    //Notification.create : 경기 참여한 유저 조회 User
    @Query("SELECT u.joinUser FROM GameUser u WHERE u.joinGame = :joinGame")
    List<User> findByJoinGame(Game joinGame);

    //isFull : 참가 신청하려는 경기의 팀 인원 조회
    Long countByJoinGameAndTeam(Game joinGame, GameTeam gameTeam);

    List<GameUser> findByJoinUser(User user);

    @Query("SELECT u FROM GameUser u, Game g WHERE u.joinGame = g and u.joinUser.id = :userId and g.startedAt > :now and (g.status = 0 or g.status = 1) ORDER BY g.status, g.startedAt DESC")
    List<GameUser> findByBeforeGame(Long userId, LocalDateTime now);

    @Query("SELECT u FROM GameUser u, Game g WHERE u.joinGame = g and u.joinUser.id = :userId and g.startedAt < :now and (g.status = 1 or g.status = 2) ORDER BY g.status, g.startedAt DESC")
    List<GameUser> findByAfterGame(Long userId, LocalDateTime now);

    //history : 경기 결과가 존재하는 경기 조회 - 전적 조회
    @Query("SELECT u FROM GameUser u, Game g WHERE u.joinGame = g and u.joinUser.id = :userId and g.result is not null ORDER BY g.startedAt")
    List<GameUser> findByGameResult(Long userId);


}
