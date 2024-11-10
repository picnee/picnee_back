package com.picnee.travel.api.in;

import com.picnee.travel.domain.notification.dto.res.FindNotificationRes;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "notifications", description = "notification API")
public interface NotificationApi {

    @Operation(summary = "안 읽은 알림 목록", description = "안 읽은 알림 목록을 표기한다.")
    public ResponseEntity<List<FindNotificationRes>> getUnreadNotifications(AuthenticatedUserReq auth);

    @Operation(summary = "알림 읽음 상태로 변경", description = "알림을 읽음 상태로 변경한다.")
    public ResponseEntity<String> updateNotificationIsRead(UUID notificationId, AuthenticatedUserReq auth);
}
