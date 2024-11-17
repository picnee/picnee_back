package com.picnee.travel.api;

import com.picnee.travel.api.in.TokenApi;
import com.picnee.travel.domain.token.dto.CreateOauthToken;
import com.picnee.travel.domain.token.service.TokenService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController implements TokenApi {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<UserRes> createOauthToken(@RequestBody CreateOauthToken dto, HttpServletResponse response) {
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

        return ResponseEntity.status(OK).body(res.getUserRes());
    }

    @PostMapping("/reissue")
    public ResponseEntity<AccessTokenRes> reGenerateToken(@AuthenticatedUser AuthenticatedUserReq auth,
                                                          @RequestHeader("RefreshToken") String refreshToken,
                                                          HttpServletResponse response) {
        AccessTokenRes res = tokenService.reissueToken(auth, refreshToken);

        ResponseCookie accessTokenCookie = ResponseCookie.from("ACCESS_TOKEN", res.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());

        return ResponseEntity.status(OK).build();
    }
}
