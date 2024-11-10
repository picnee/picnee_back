package com.picnee.travel.domain.notification.dto.event;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.entity.User;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class PostLikeEvent implements NotificationEvent {
    private final UUID postId;

    @Override
    public UUID getTargetId() {
        return postId;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.POST_LIKE;
    }

    @Override
    public Notification toEntity(User user) {
        return Notification.builder()
                .notificationType(NotificationType.POST_LIKE)
                .targetId(postId)
                .isRead(false)
                .user(user)
                .build();
    }
}
