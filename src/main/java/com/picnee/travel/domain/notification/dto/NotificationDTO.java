package com.picnee.travel.domain.notification.dto;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.entity.User;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class NotificationDTO {
    private UUID                id;
    private NotificationType    notificationType;
    private UUID                targetId;
    private boolean             isRead;

    public static Notification toEntity(NotificationDTO dto, User user) {
        return Notification.builder()
                .id(dto.getId())
                .notificationType(dto.getNotificationType())
                .targetId(dto.getTargetId())
                .isRead(dto.isRead())
                .user(user)
                .build();
    }
}
