package com.picnee.travel.domain.notification.eventListener;

import com.picnee.travel.domain.notification.dto.event.PostCommentEvent;
import com.picnee.travel.domain.notification.dto.event.PostLikeEvent;
import com.picnee.travel.domain.notification.dto.event.ReviewScoreEvent;
import com.picnee.travel.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final NotificationService notificationService;

    @TransactionalEventListener
    public void handlePostCommentEvent(PostCommentEvent event) {
        notificationService.create(event);
    }

    @TransactionalEventListener
    public void handlePostLikeEvent(PostLikeEvent event) {
        notificationService.create(event);
    }

    @TransactionalEventListener
    public void handleReviewScoreEvent(ReviewScoreEvent event) {
        notificationService.create(event);
    }
}
