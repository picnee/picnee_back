package com.picnee.travel.domain.token.service;

import com.picnee.travel.domain.token.dto.CreateOauthToken;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.NotValidRefreshTokenException;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.redis.service.RedisService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.picnee.travel.global.exception.ErrorCode.NOT_VALID_REFRESH_TOKEN_EXCEPTION;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final RedisService redisService;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    public JwtTokenRes createOauthToken(CreateOauthToken dto) {
        User user = userService.findByEmail(redisService.getValue(dto.getAuthUUID()));
        // 조회 후 삭제
        redisService.deleteValue(dto.getAuthUUID());

        JwtTokenRes jwtTokenRes = tokenProvider.generateOAuthToken(user);
        redisService.saveValue(user.getEmail(), jwtTokenRes.getRefreshToken());

        return jwtTokenRes;
    }

    /**
     * accessToken 재발급
     */
    public AccessTokenRes reissueToken(AuthenticatedUserReq auth, String refreshToken, HttpServletResponse response) {
        String token = redisService.getValue(auth.getEmail());

        if(refreshToken.equals(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            String accessToken = tokenProvider.generateAccessToken(authentication);

            return AccessTokenRes.from(accessToken);
        }

        throw new NotValidRefreshTokenException(NOT_VALID_REFRESH_TOKEN_EXCEPTION);

    }
}
