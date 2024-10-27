package com.picnee.travel.global.config;

import com.picnee.travel.global.jwt.filter.JwtFilter;
import com.picnee.travel.global.jwt.handler.JwtAccessDeniedHandler;
import com.picnee.travel.global.jwt.handler.JwtAuthenticationEntryPoint;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.oauth.CustomOauth2UserService;
import com.picnee.travel.global.oauth.OAuth2UserSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Slf4j
@Configuration
@EnableWebSecurity // 스프링 씨큐리티의 필터 체인이 동작하여 요청을 인가 요청
@EnableMethodSecurity // 서비스 요청에서 메서드 호출 시 보안검사를 수행할 수 있도록 요청
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomOauth2UserService customOauth2UserService;
    private final OAuth2UserSuccessHandler oAuth2UserSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 설정 off
                .addFilterBefore(corsConfig.corsFilter(), UsernamePasswordAuthenticationFilter.class) //CorsFilter
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(POST, "/users").permitAll()
                        .requestMatchers(POST, "/users/login").permitAll()
                        .anyRequest().permitAll())

                .oauth2Login(
                        oauth -> oauth
                                .userInfoEndpoint(config ->
                                        config.userService(customOauth2UserService))
                                .successHandler(oAuth2UserSuccessHandler)
                )

                .sessionManagement(session ->
                        session.sessionCreationPolicy(STATELESS))
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행

        return http.build();
    }
}
