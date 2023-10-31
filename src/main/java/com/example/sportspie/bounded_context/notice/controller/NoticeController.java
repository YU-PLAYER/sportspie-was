package com.example.sportspie.bounded_context.notice.controller;

import com.example.sportspie.base.api.NoticeApi;
import com.example.sportspie.bounded_context.notice.entity.Notice;
import com.example.sportspie.bounded_context.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController implements NoticeApi {
    private final NoticeService noticeService;

    @Override
    public List<Notice> noticeList() {
        return noticeService.noticeList();
    }

    @Override
    public Notice noticeDetail(Integer id) {
        return noticeService.noticeDetail(id);
    }
}
