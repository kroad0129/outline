package com.outline.outline.notification;

import com.outline.outline.notification.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification", description = "공지 관련 API")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    @Operation(summary = "공지알람", description = "공지알람을 확인합니다.")
    public List<NotificationResponse> getNotifications(@PathVariable Long userId) {
        return notificationService.getUserNotifications(userId).stream().map(NotificationResponse::from).toList();
    }
}
