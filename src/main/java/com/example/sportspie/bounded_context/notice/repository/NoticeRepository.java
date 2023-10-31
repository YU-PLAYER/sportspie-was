package com.example.sportspie.bounded_context.notice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sportspie.bounded_context.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer>{

}
