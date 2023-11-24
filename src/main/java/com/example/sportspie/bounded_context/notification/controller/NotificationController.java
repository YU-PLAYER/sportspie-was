package com.example.sportspie.bounded_context.notification.controller;

import com.example.sportspie.base.api.NotificationApi;
import com.example.sportspie.bounded_context.notification.dto.NotificationResponseDto;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import com.example.sportspie.bounded_context.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;

    @Override
    public List<NotificationResponseDto> list(Long receiverId) {
        return notificationService.list(receiverId);
    }

    @Override
    public Notification delete(Long id) {
        return notificationService.delete(id);
    }
}
