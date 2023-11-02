package com.example.sportspie.bounded_context.notification.dto;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import com.example.sportspie.bounded_context.notification.type.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationRequestDto {
    private Long receiverId;
    private NotificationType type;

    @Builder
    public NotificationRequestDto(Long receiverId, NotificationType type) {
        this.receiverId = receiverId;
        this.type = type;
    }

    public Notification toEntity(User receiver){
        return Notification.builder()
                .receiver(receiver)
                .type(type)
                .build();
    }
}
