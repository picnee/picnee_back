package com.picnee.travel.api.in;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.dto.req.UpdateUserNicknameReq;
import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "users", description = "user API")
public interface UserApi {
    @Operation(summary = "회원가입", description = "회원을 가입한다.")
    public ResponseEntity<Void> createUser(CreateUserReq dto);

    @Operation(summary = "로그인", description = "로그인 한다.")
    public ResponseEntity<JwtTokenRes> loginUser(LoginUserReq dto, HttpServletResponse response);

    @Operation(summary = "닉네임 설정", description = "OAuth 로그인 한 사람 중 닉네임을 설정하지 않은 사용자의 닉네임을 설정하게 한다.")
    public ResponseEntity<String> updateNickname(AuthenticatedUserReq auth, UpdateUserNicknameReq dto);
}