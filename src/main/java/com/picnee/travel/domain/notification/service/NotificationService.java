package com.picnee.travel.domain.notification.service;

import com.picnee.travel.domain.notification.dto.NotificationDTO;
import com.picnee.travel.domain.notification.dto.res.GetNotificationRes;
import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.repository.NotificationRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class NotificationService {

    private NotificationRepository notificationRepository;
    private UserService userService;

    /**
     * 알림 생성
     */
    @Transactional
    public void create(NotificationDTO dto, AuthenticatedUserReq auth) {
        User user = userService.findByEmail(auth.getEmail());
        notificationRepository.save(NotificationDTO.toEntity(dto, user));
    }

    /**
     * 안 읽은 알림 목록
     * */
    public List<GetNotificationRes> getUnreadNotifications(AuthenticatedUserReq auth) {
        List<Notification> notifications = notificationRepository.findByUnreadNotifications();

        return GetNotificationRes.toNotificationResList(notifications);
    }
}
