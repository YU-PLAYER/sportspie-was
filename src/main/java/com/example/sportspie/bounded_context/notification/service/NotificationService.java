package com.example.sportspie.bounded_context.notification.service;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.notification.dto.NotificationRequestDto;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import com.example.sportspie.bounded_context.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public List<Notification> list(Long receiverId){
        return notificationRepository.findByReceiverId(receiverId);
    }

    public Notification delete(Long notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알람이 없습니다. id = " + notificationId));
        notificationRepository.delete(notification);
        return notification;
    }

    public Notification create(NotificationRequestDto notificationDto){
        User receiver = userService.read(notificationDto.getReceiverId());
        return notificationRepository.save(notificationDto.toEntity(receiver));
    }

}

