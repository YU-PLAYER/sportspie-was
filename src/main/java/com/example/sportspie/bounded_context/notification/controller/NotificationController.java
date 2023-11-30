package com.example.sportspie.bounded_context.notification.controller;

import com.example.sportspie.base.api.NotificationApi;
import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.base.jwt.util.JwtProvider;
import com.example.sportspie.bounded_context.notification.dto.NotificationResponseDto;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import com.example.sportspie.bounded_context.notification.service.NotificationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {
    private final NotificationService notificationService;
    private final JwtProvider jwtProvider;

    @Override
    public List<NotificationResponseDto> list(HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return notificationService.list(userId);
    }

    @Override
    public ResponseEntity<StateResponse> delete(Long id, HttpServletRequest request) {
        Long userId = jwtProvider.getUserId(jwtProvider.resolveToken(request).substring(7));
        return notificationService.delete(userId, id);
    }
}
