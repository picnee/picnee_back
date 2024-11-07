package com.picnee.travel.api.in;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "users", description = "user API")
public interface UserApi {
    @Operation(summary = "회원가입", description = "회원을 가입한다.")
    public ResponseEntity<Void> createUser(CreateUserReq dto);

    @Operation(summary = "로그인", description = "로그인 한다.")
    public ResponseEntity<JwtTokenRes> loginUser(LoginUserReq dto);

    @Operation(summary = "토큰 재발급", description = "accessToken을 재발급 한다.")
    public ResponseEntity<AccessTokenRes> reGenerateToken(AuthenticatedUserReq auth, String refreshToken);
}