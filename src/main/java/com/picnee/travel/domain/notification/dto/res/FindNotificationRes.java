package com.picnee.travel.domain.notification.dto.res;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class GetNotificationRes {

    private UUID                notificationId;
    private NotificationType    notificationType;
    private UUID                targetId;
    private boolean             isRead;
    private LocalDateTime       createdAt;
    private UserRes             userRes;

    public static GetNotificationRes from(Notification notification) {
        return GetNotificationRes.builder()
                .notificationId(notification.getId())
                .notificationType(notification.getNotificationType())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .userRes(UserRes.from(notification.getUser()))
                .build();
    }

    public static List<GetNotificationRes> toNotificationResList(List<Notification> notifications) {
        return notifications.stream()
                .map(notification -> GetNotificationRes.builder()
                        .notificationId(notification.getId())
                        .notificationType(notification.getNotificationType())
                        .targetId(notification.getTargetId())
                        .isRead(notification.isRead())
                        .userRes(UserRes.from(notification.getUser()))
                        .createdAt(notification.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
