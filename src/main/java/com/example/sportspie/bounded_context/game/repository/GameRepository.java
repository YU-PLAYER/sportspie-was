package com.example.sportspie.bounded_context.game.repository;

import com.example.sportspie.bounded_context.game.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {

//    @Query("SELECT g FROM Game g WHERE g.createdAt BETWEEN :start AND :end AND (:title IS NULL OR g.title = :title)")
    //Page<Game> findByStartedAtBetweenAndTitleOrTitleIsEmpty(LocalDateTime start, LocalDateTime end, String title, Pageable pageable);
//    findByTitleAndCreatedAtBetween(Optional<String> title, LocalDateTime startDate, LocalDateTime endDate);
    Page<Game> findByStartedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    Page<Game> findByStartedAtBetweenAndTitleContaining(LocalDateTime start, LocalDateTime end, String keyword, Pageable pageable);
}
