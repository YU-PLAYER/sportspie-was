package com.example.sportspie.bounded_context.game.repository;

import com.example.sportspie.bounded_context.game.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;

public interface GameRepository extends JpaRepository<Game, Long> {
    Page<Game> findByStartedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<Game> findByTitleContaining(String keyword, Pageable pageable);
}
