package com.example.sportspie.base.api;

import com.example.sportspie.bounded_context.notification.dto.NotificationRequestDto;
import com.example.sportspie.bounded_context.notification.entity.Notification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/notification")
@Tag(name = "Notification", description = "알림 관련 API")
public interface NotificationApi {
    @GetMapping("/{receiverId}")
    @Operation(summary = "알림 목록 조회 메서드", description = "사용자의 알림 목록을 조회하기 위한 메서드입니다.")
    List<Notification> list(@PathVariable Long receiverId);

    @DeleteMapping("/{id}")
    @Operation(summary = "알림 삭제 메서드", description = "사용자의 알림을 삭제하기 위한 메서드입니다.")
    Notification delete(@PathVariable Long id);

    @PostMapping("/post")
    @Operation(summary = "알림 생성 메서드", description = "사용자의 알림을 생성하기 위한 메서드입니다.")
    Notification create(@RequestBody NotificationRequestDto notificationRequestDto);

}
