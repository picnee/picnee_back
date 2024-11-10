package com.picnee.travel.domain.notification.dto.event;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.user.entity.User;

import java.util.UUID;

public interface NotificationEvent {
    UUID getTargetId();
    NotificationType getNotificationType();
    Notification toEntity(User user);
}
