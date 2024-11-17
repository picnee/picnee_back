package com.picnee.travel.api;

import com.picnee.travel.api.in.TokenApi;
import com.picnee.travel.domain.token.dto.CreateOauthToken;
import com.picnee.travel.domain.token.service.TokenService;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController implements TokenApi {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<JwtTokenRes> createOauthToken(@RequestBody CreateOauthToken dto, HttpServletResponse response) {
        JwtTokenRes res = tokenService.createOauthToken(dto);

        ResponseCookie accessTokenCookie = ResponseCookie.from("ACCESS_TOKEN", res.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60)
                .sameSite("None")
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("REFRESH_TOKEN", res.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 7)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.status(CREATED).build();
    }
}
