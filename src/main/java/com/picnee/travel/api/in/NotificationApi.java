package com.picnee.travel.api.in;

import com.picnee.travel.domain.notification.dto.res.GetNotificationRes;
import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "notifications", description = "notification API")
public interface NotificationApi {

    @Operation(summary = "안 읽은 알림 목록", description = "안 읽은 알림 목록을 표기한다.")
    public ResponseEntity<List<GetNotificationRes>> getUnreadNotifications(AuthenticatedUserReq auth);
}
