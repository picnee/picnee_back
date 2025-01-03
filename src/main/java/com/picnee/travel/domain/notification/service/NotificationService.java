package com.picnee.travel.domain.notification.service;

import com.picnee.travel.domain.notification.dto.event.NotificationEvent;
import com.picnee.travel.domain.notification.dto.res.FindNotificationRes;
import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.notification.exception.NotFoundNotificationException;
import com.picnee.travel.domain.notification.exception.NotNotificationRecipientException;
import com.picnee.travel.domain.notification.repository.NotificationRepository;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.service.PostCommentService;
import com.picnee.travel.domain.review.service.ReviewService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.NOT_FOUND_NOTIFICATION_EXCEPTION;
import static com.picnee.travel.global.exception.ErrorCode.NOT_NOTIFICATION_RECIPIENT_EXCEPTION;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ReviewService reviewService;
    private final PostService postService;
    private final PostCommentService postCommentService;

    // TODO 본인이 발행한 알림(본인이 작성한 게시글에 본인이 댓글 작성 등)은 뜨지 않게 하기
    // TODO 중복 알림은 안 뜨게 하기(좋아요 토글 같은 경우는 적용 필요)

    /**
     * 알림 생성
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Notification create(NotificationEvent event) {
        User user = findUserByTargetIdAndType(event.getTargetId(), event.getNotificationType());

        return notificationRepository.save(event.toEntity(user));
    }

    /**
     * 알림 읽음
     * */
    @Transactional
    public Notification updateIsRead(UUID notificationId, AuthenticatedUserReq auth) {
        Notification notification = findById(notificationId);

        checkNotificationRecipient(notification, auth.getId());

        notification.updateIsRead();

        return notification;
    }

    /**
     * 안 읽은 알림 목록
     * */
    public List<FindNotificationRes> getUnreadNotifications(AuthenticatedUserReq auth) {
        List<Notification> notifications = notificationRepository.findByUnreadNotifications(auth.getId());

        return FindNotificationRes.toNotificationResList(notifications);
    }

    public Notification findById(UUID notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotFoundNotificationException(NOT_FOUND_NOTIFICATION_EXCEPTION));
    }

    public User findUserByTargetIdAndType(UUID targetId, NotificationType notificationType) {
        return switch (notificationType) {
            case POST_COMMENT -> postService.findById(targetId)
                    .getUser();
            case COMMENT_LIKE -> postCommentService.findById(targetId)
                    .getUser();
            case REVIEW_SCORE -> reviewService.findById(targetId)
                    .getUser();
        };
    }

    public void checkNotificationRecipient(Notification notification, UUID userId) {
        if (!notification.getUser().getId().equals(userId)) {
            throw new NotNotificationRecipientException(NOT_NOTIFICATION_RECIPIENT_EXCEPTION);
        }
    }
}
