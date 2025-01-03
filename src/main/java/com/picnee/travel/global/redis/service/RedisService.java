package com.picnee.travel.global.redis.service;

import com.picnee.travel.global.jwt.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisService {

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Redis refreshToken 값 등록 / 수정
     */
    public void saveValue(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, Duration.ofSeconds(jwtProperties.getRefreshValidityInSeconds()));
    }

    /**
     * Redis authToken 코드 등록
     */
    public void saveAuthValue(final String authToken, final String email) {
        redisTemplate.opsForValue().set(authToken, email);
        redisTemplate.expire(authToken, Duration.ofSeconds(jwtProperties.getAuthValidityInSeconds()));
    }

    /**
     * Redis 값 조회
     */
    public String getValue(String key) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        if (values.get(key) == null) return "";
        return String.valueOf(values.get(key));
    }

    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }
}
