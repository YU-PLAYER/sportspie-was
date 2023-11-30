package com.example.sportspie.bounded_context.notification.dto;

import com.example.sportspie.bounded_context.notification.type.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {
    private Long id;
    private String content;
    private LocalDate date;
    private LocalTime time;
    private String stadiumName;
    private NotificationType type;

    @Builder
    public NotificationResponseDto(Long id, String content, LocalDate date, LocalTime time, String stadiumName, NotificationType type) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
        this.stadiumName = stadiumName;
        this.type = type;
    }
}
