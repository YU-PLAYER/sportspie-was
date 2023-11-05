package com.example.sportspie.bounded_context.game.repository;

import com.example.sportspie.bounded_context.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByStartedAtBetween(LocalDateTime start, LocalDateTime end);
}
