package com.picnee.travel.api;

import com.picnee.travel.api.in.NotificationApi;
import com.picnee.travel.domain.notification.dto.res.FindNotificationRes;
import com.picnee.travel.domain.notification.entity.Notification;
import com.picnee.travel.domain.notification.service.NotificationService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService notificationService;

    @GetMapping("/unread")
    public ResponseEntity<List<FindNotificationRes>> getUnreadNotifications(@AuthenticatedUser AuthenticatedUserReq auth) {
        List<FindNotificationRes> notifications = notificationService.getUnreadNotifications(auth);
        return ResponseEntity.status(OK).body(notifications);
    }

    @PatchMapping("/{notificationId}")
    public ResponseEntity<String> updateNotificationIsRead(@PathVariable("notificationId") UUID notificationId
                                                         , @AuthenticatedUser AuthenticatedUserReq auth) {
        Notification notification = notificationService.updateIsRead(notificationId, auth);
        return ResponseEntity.status(OK).body(notification.getId().toString());
    }
}
