package com.picnee.travel.global.oauth;

import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.NotFoundEmailException;
import com.picnee.travel.domain.user.repository.UserRepository;
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
import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2CustomUser oAuth2CustomUser = (OAuth2CustomUser) authentication.getPrincipal();

        // 기존 유저인 경우 로그인 성공 응답 처리
        User user = userRepository.findByEmail(oAuth2CustomUser.getName()).orElseThrow(()
                -> new NotFoundEmailException(NOT_FOUND_EMAIL_EXCEPTION));

        // auth redis 저장
        String authToken = UUID.randomUUID().toString();
        redisService.saveAuthValue(authToken, user.getEmail());

        // TODO 로그인 유지되는지 확인
        if (user.isDefaultNickname()) {
            String redirectUrl = String.format("/nickname?code=%s", authToken);
            response.setHeader("Location", redirectUrl);
            response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            return;
        }

        String redirectUrl = String.format("http://localhost:3000/code=%s", authToken);
        response.sendRedirect(redirectUrl);
    }
}
