package com.picnee.travel.domain.notification.dto.event;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

@Builder
@AllArgsConstructor
public class ReviewScoreEvent implements NotificationEvent {
    private final UUID reviewId;

    @Override
    public UUID getTargetId() {
        return reviewId;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.REVIEW_SCORE;
    }

    @Override
    public Notification toEntity(User user) {
        return Notification.builder()
                .notificationType(NotificationType.REVIEW_SCORE)
                .targetId(reviewId)
                .isRead(false)
                .user(user)
                .build();
    }
}