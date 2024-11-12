package com.picnee.travel.global.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.NotFoundEmailException;
import com.picnee.travel.domain.user.repository.UserRepository;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.filter.JwtFilter;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.redis.service.RedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2CustomUser oAuth2CustomUser = (OAuth2CustomUser) authentication.getPrincipal();

        // 기존 유저인 경우 로그인 성공 응답 처리
        User user = userRepository.findByEmail(oAuth2CustomUser.getName()).orElseThrow(()
                -> new NotFoundEmailException(NOT_FOUND_EMAIL_EXCEPTION));

        if (user.isDefaultNickname()) {
            getRedirectStrategy().sendRedirect(request, response, "/nickname");
            return;
        }

        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        JwtTokenRes jwtTokenRes = JwtTokenRes.from(accessToken, refreshToken, user);
        redisService.saveValue(user.getEmail(), jwtTokenRes.getRefreshToken());

        createResponseHandler(response, jwtTokenRes);

        response.sendRedirect("/");
    }

    private void createResponseHandler(HttpServletResponse response, JwtTokenRes jwtTokenRes) throws IOException {
        response.addHeader(JwtFilter.ACCESS_AUTHORIZATION_HEADER, "Bearer " + jwtTokenRes.getAccessToken());
        response.getWriter().write(objectMapper.writeValueAsString(jwtTokenRes));
    }
}
