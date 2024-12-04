package com.picnee.travel.domain.user.service;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.entity.CreateTestUser;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.LoginFailedException;
import com.picnee.travel.domain.user.exception.NotFoundEmailException;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    CreateTestUser createTestUser;

    private AuthenticatedUserReq user;
    private AuthenticatedUserReq anotherUser;
    private JwtTokenRes jwtTokenRes;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;

    @BeforeEach
    void setUp() throws Exception {
        user = createTestUser.createUser();
        anotherUser = createTestUser.createAnotherUser();
        jwtTokenRes = createTestUser.createJwtToken();
    }

    @Test
    @DisplayName("유저 생성 성공")
    void test1() {
        CreateUserReq createUserReq = CreateUserReq.builder()
                .email("test@gmail.com")
                .nickname("testName")
                .password("abcd1234!")
                .isMarketing(true)
                .isAlarm(true)
                .build();

        User testUser = userService.create(createUserReq);

        assertThat(testUser).isNotNull();
        assertThat(testUser.getEmail()).isEqualTo("test@gmail.com");
        assertThat(testUser.getNickname()).isEqualTo("testName");
        assertThat(passwordEncoder.matches("abcd1234!", testUser.getPassword())).isTrue();
        assertThat(testUser.getIsMarketing()).isTrue();
        assertThat(testUser.getIsAlarm()).isTrue();
    }

    @Test
    @DisplayName("로그인")
    void test2() {
        String email = "testUser@naver.com";
        String password = "abcd1234!";
        LoginUserReq loginUserReq = LoginUserReq.builder()
                .email(email)
                .password(password)
                .build();

        JwtTokenRes jwtTokenRes = userService.login(loginUserReq);

        assertThat(tokenProvider.validateToken(jwtTokenRes.getAccessToken())).isTrue();
        assertThat(tokenProvider.validateToken(jwtTokenRes.getRefreshToken())).isTrue();
        assertThat(jwtTokenRes.getUserRes().getNickName()).isEqualTo("tester");
    }

    @Test
    @DisplayName("비밀번호 미일치 로그인 실패")
    void test3() {
        String email = "testUser@naver.com";
        String failPassword = "failPassword";
        LoginUserReq failLoginUserReq = LoginUserReq.builder()
                .email(email)
                .password(failPassword)
                .build();

        assertThatThrownBy(() -> userService.login(failLoginUserReq))
                .isInstanceOf(LoginFailedException.class)
                .hasMessageContaining("비밀번호");
    }

    @Test
    @DisplayName("갖고있지 않은 이메일 정보")
    void test4() {
        String failEmail = "unknown@naver.com";
        String password = "abcd1234!";
        LoginUserReq loginUserReq = LoginUserReq.builder()
                .email(failEmail)
                .password(password)
                .build();

        assertThatThrownBy(() -> userService.login(loginUserReq))
                .isInstanceOf(NotFoundEmailException.class)
                .hasMessage("존재하지 않는 계정입니다.");
    }

}
