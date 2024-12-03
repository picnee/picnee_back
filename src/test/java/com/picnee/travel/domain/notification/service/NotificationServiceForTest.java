package com.picnee.travel.domain.notification.service;

import com.picnee.travel.domain.notification.dto.event.NotificationEvent;
import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.repository.NotificationRepository;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.postComment.service.PostCommentService;
import com.picnee.travel.domain.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@Profile("test")
@Primary
public class NotificationServiceForTest extends NotificationService {

    public NotificationServiceForTest(NotificationRepository notificationRepository,
                                   ReviewService reviewService,
                                   PostService postService,
                                   PostCommentService postCommentService) {
        super(notificationRepository, reviewService, postService, postCommentService);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Notification create(NotificationEvent event) {
        return super.create(event);
    }
}
