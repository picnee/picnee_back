package com.picnee.travel.domain.notification.service;

import com.picnee.travel.domain.notification.dto.event.NotificationEvent;
import com.picnee.travel.domain.notification.dto.res.GetNotificationRes;
import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.notification.repository.NotificationRepository;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.review.service.ReviewService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;
    private UserService userService;
    private ReviewService reviewService;
    private PostService postService;

    /**
     * 알림 생성
     */
    @Transactional
    public void create(NotificationEvent event) {
        User user = findUserByTargetIdAndType(event.getTargetId(), event.getNotificationType());

        notificationRepository.save(event.toEntity(user));
    }

    /**
     * 알림 읽음
     * */

    /**
     * 안 읽은 알림 목록
     * */
    public List<GetNotificationRes> getUnreadNotifications(AuthenticatedUserReq auth) {
        List<Notification> notifications = notificationRepository.findByUnreadNotifications();

        return GetNotificationRes.toNotificationResList(notifications);
    }

    public User findUserByTargetIdAndType(UUID targetId, NotificationType notificationType) {
        return switch (notificationType) {
            case POST_COMMENT, POST_LIKE -> postService.findById(targetId)
                    .getUser();
            case REVIEW_SCORE -> reviewService.findById(targetId)
                    .getUser();
        };
    }
}
