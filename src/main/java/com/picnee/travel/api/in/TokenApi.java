package com.picnee.travel.api.in;

import com.picnee.travel.domain.token.dto.CreateOauthToken;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "token", description = "token API")
public interface TokenApi {

    @Operation(summary = "OAuth token 발급", description = "소셜 로그인 할 경우 Token 정보 전달")
    public ResponseEntity<JwtTokenRes> createOauthToken(CreateOauthToken dto, HttpServletResponse response);

    @Operation(summary = "토큰 재발급", description = "accessToken을 재발급 한다.")
    public ResponseEntity<AccessTokenRes> reGenerateToken(AuthenticatedUserReq auth, String refreshToken, HttpServletResponse response);
}
