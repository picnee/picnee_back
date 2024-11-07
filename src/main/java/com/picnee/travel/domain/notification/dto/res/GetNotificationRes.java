package com.picnee.travel.domain.notification.dto.res;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class GetNotificationRes {

    private UUID                id;
    private NotificationType    notificationType;
    private UUID                targetId;
    private boolean             isRead;
    private LocalDateTime       createdAt;
    private UserRes             userRes;

    public static GetNotificationRes from(Notification notification) {
        return GetNotificationRes.builder()
                .id(notification.getId())
                .notificationType(notification.getNotificationType())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .userRes(UserRes.from(notification.getUser()))
                .build();
    }
}
