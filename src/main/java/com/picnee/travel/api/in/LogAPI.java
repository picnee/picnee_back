package com.picnee.travel.api.in;

import com.picnee.travel.domain.post.dto.req.CreatePostReq;
import com.picnee.travel.domain.post.dto.req.ModifyPostReq;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "logs", description = "log API")
public interface LogAPI {
    @Operation(summary = "로그 파일", description = "로그를 받아온다.")
    public ResponseEntity<Resource> getLogFile();
}
