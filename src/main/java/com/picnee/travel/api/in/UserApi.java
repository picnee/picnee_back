package com.picnee.travel.api.in;

import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "users", description = "user API")
public interface UserApi {
    @Operation(summary = "회원가입", description = "회원을 가입한다.")
    public ResponseEntity<Void> createUser(CreateUserReq dto);
}

