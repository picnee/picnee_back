package com.picnee.travel.api;

import com.picnee.travel.api.in.NotificationApi;
import com.picnee.travel.domain.notification.dto.res.GetNotificationRes;
import com.picnee.travel.domain.notification.service.NotificationService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private NotificationService notificationService;

    @GetMapping("/unread")
    public ResponseEntity<List<GetNotificationRes>> getUnreadNotifications(AuthenticatedUserReq auth) {
        List<GetNotificationRes> notifications = notificationService.getUnreadNotifications(auth);
        return ResponseEntity.status(OK).body(notifications);
    }
}
