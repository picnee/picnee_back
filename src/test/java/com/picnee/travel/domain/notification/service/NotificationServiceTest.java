package com.picnee.travel.domain.notification.service;

import com.picnee.travel.domain.notification.dto.event.PostCommentEvent;
import com.picnee.travel.domain.notification.dto.res.FindNotificationRes;
import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.entity.NotificationType;
import com.picnee.travel.domain.post.entity.CreateTestPost;
import com.picnee.travel.domain.post.entity.Post;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.CreateTestUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class NotificationServiceTest {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    CreateTestUser createTestUser;

    @Autowired
    CreateTestPost createTestPost;

    private AuthenticatedUserReq user;
    private Post post;
    private Notification notification;

    @BeforeEach
    void setUp() throws Exception {
        user = createTestUser.createUser();
        post = createTestPost.createPost(user);
        notification = notificationService.create(new PostCommentEvent(post.getId()));
    }

    @Test
    @DisplayName("알림 생성 : 게시글에 댓글 알림 생성")
    void test1() {
        assertThat(notification.getUser().getId()).isEqualTo(user.getId());
        assertThat(notification.getTargetId()).isEqualTo(post.getId());
        assertThat(notification.getNotificationType()).isEqualTo(NotificationType.POST_COMMENT);
    }

    @Test
    @DisplayName("안 읽은 알림 확인 : 게시글에 댓글 작성 후 안 읽은 알림 확인")
    void test2() {
        List<FindNotificationRes> notificationList = notificationService.getUnreadNotifications(user);

        assertThat(notificationList.size()).isEqualTo(1);
    }
}
