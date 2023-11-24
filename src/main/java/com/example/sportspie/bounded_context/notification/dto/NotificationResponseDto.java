package com.example.sportspie.bounded_context.notification.dto;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import com.example.sportspie.bounded_context.notification.type.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class NotificationRequestDto {
    private String content;
    private LocalDate date;
    private LocalTime time;
    private String stadiumName;
    private NotificationType type;

    @Builder
    public NotificationRequestDto(String content, NotificationType type) {
        this.content = content;
        this.type = type;
    }
}
