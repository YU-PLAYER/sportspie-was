package com.example.sportspie.bounded_context.notification.service;

import com.example.sportspie.base.error.StateResponse;
import com.example.sportspie.bounded_context.auth.entity.User;
import com.example.sportspie.bounded_context.auth.service.UserService;
import com.example.sportspie.bounded_context.game.entity.Game;
import com.example.sportspie.bounded_context.gameUser.repository.GameUserRepository;
import com.example.sportspie.bounded_context.notification.dto.NotificationResponseDto;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import com.example.sportspie.bounded_context.notification.repository.NotificationRepository;
import com.example.sportspie.bounded_context.notification.type.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final GameUserRepository gameUserRepository;

    /**
     * 알람 목록 조회
     * @param receiverId
     * @return
     */
    public List<NotificationResponseDto> list(Long receiverId){
        User user = userService.read(receiverId);
        return notificationRepository.findByReceiverOrderByCreatedAtDesc(user).stream()
                .map(notification -> notification.toDto())
                .collect(Collectors.toList());
    }

    /**
     * 알람 삭제
     * @param notificationId
     * @return
     */
    public ResponseEntity<StateResponse> delete(Long userId, Long notificationId){
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알람이 없습니다. id = " + notificationId));
        if(notification.getReceiver().getId() != userId) throw new IllegalArgumentException("해당 알람이 없습니다. id = " + notificationId);

        notificationRepository.delete(notification);

        return ResponseEntity.ok(StateResponse.builder().code("SUCCESS").message("알람 삭제가 성공적으로 완료되었습니다.").build());
    }

    /**
     * 경기별 알람 생성
     */
    public void create(Game game, NotificationType notificationType){
        notificationRepository.saveAll(gameUserRepository.findByJoinGame(game).stream()
                .map(user -> Notification.builder()
                        .receiver(user)
                        .fromGame(game)
                        .type(notificationType)
                        .build()).collect(Collectors.toList()));
    }

}

