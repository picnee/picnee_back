package com.picnee.travel.api.in;

import com.picnee.travel.domain.token.dto.CreateOauthToken;
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
}
