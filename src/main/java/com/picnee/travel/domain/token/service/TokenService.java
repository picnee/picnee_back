package com.picnee.travel.domain.token.service;

import com.picnee.travel.domain.token.dto.CreateOauthToken;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
