package com.example.sportspie.base.api;

import com.example.sportspie.bounded_context.notice.entity.Notice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RequestMapping("/api/notice")
@Tag(name = "Notice", description = "공지사항 API")
public interface NoticeApi {
    @GetMapping("")
    @Operation(summary = "공지사항 목록 조회 메서드", description = "사용자가 공지사항 목록을 조회하기 위한 메서드입니다.")
    List<Notice> noticeList();


    @GetMapping("/{id}")
    @Operation(summary = "공지사항 상세 조회 메서드", description = "사용자가 공지사항의 상세 내용을 조회하기 위한 메서드입니다.")
    Notice noticeDetail(@PathVariable Integer id);
}
