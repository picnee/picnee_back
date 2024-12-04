package com.picnee.travel.domain.notification.dto.event;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.entity.User;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class CommentLikeEvent implements NotificationEvent {
    private final UUID commentId;

    @Override
    public UUID getTargetId() {
        return commentId;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.COMMENT_LIKE;
    }

    @Override
    public Notification toEntity(User user) {
        return Notification.builder()
                .notificationType(NotificationType.COMMENT_LIKE)
                .targetId(commentId)
                .isRead(false)
                .user(user)
                .build();
    }
}
