package com.picnee.travel.domain.notification.repository;

import com.picnee.travel.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationRepositoryCustom {
    List<Notification> findByUnreadNotifications();
}
