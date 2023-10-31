package com.example.sportspie.bounded_context.notice.service;

import com.example.sportspie.bounded_context.notice.entity.Notice;
import com.example.sportspie.bounded_context.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;


    public List<Notice> noticeList(){
        return noticeRepository.findAll();
    }

    public Notice noticeDetail(Integer id){
        return noticeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 없습니다. id= "+ id));
    }
}
