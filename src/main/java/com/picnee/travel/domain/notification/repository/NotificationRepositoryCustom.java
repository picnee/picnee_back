package com.picnee.travel.domain.notification.repository;

import com.picnee.travel.domain.notification.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepositoryCustom {
    List<Notification> findByUnreadNotifications(UUID userId);
}
