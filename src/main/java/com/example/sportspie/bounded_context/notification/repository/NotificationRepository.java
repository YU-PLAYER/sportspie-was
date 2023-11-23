package com.example.sportspie.bounded_context.notification.repository;

import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverOrderByCreatedAt(User receiver);
}



