package com.picnee.travel.domain.notification.repository;

import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.QNotification;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notification> findByUnreadNotifications() {
        QNotification notification = QNotification.notification;

        JPAQuery<Notification> query = jpaQueryFactory
                .selectFrom(notification)
                .where(notification.isRead.isFalse())
                .orderBy(notification.createdAt.desc());

        return query.fetch();
    }
}