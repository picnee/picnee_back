package com.picnee.travel.domain.notification.dto.event;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class PostCommentEvent implements NotificationEvent {
    private final UUID postId;

    @Override
    public UUID getTargetId() {
        return postId;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.POST_COMMENT;
    }

    @Override
    public Notification toEntity(User user) {
        return Notification.builder()
                .notificationType(NotificationType.POST_COMMENT)
                .targetId(postId)
                .isRead(false)
                .user(user)
                .build();
    }
}
