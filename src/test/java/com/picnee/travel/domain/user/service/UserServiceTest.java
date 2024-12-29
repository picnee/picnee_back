package com.picnee.travel.domain.user.service;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.entity.CreateTestUser;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.LoginFailedException;
import com.picnee.travel.domain.user.exception.NotFoundEmailException;
import com.picnee.travel.domain.user.repository.UserRepository;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private UserRepository userRepository;

    /**
     * 테스트에 사용할 user 데이터
     * email : testUser@naver.com
     * nickname : tester
     * password : abcd1234!
     * 
     */
    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();
        user = createTestUser.createUser();
        anotherUser = createTestUser.createAnotherUser();
        jwtTokenRes = createTestUser.createJwtToken();
    }

    @Test
    @DisplayName("유저 생성 성공")
    void test1() {

        String email = "test@gmail.com";
        String nickname = "testName";
        String password = "abcd1234!";

        CreateUserReq createUserReq = CreateUserReq.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .isMarketing(true)
                .isAlarm(true)
                .build();

        User testUser = userService.create(createUserReq);

        assertThat(testUser).isNotNull();
        assertThat(testUser.getEmail()).isEqualTo(email);
        assertThat(testUser.getNickname()).isEqualTo(nickname);
        assertThat(passwordEncoder.matches(password, testUser.getPassword())).isTrue();
        assertThat(testUser.getIsMarketing()).isTrue();
        assertThat(testUser.getIsAlarm()).isTrue();
    }

    @Test
    @DisplayName("유저 생성 실패 - 이미 존재하는 이메일")
    @Description("유니크 제약조건 확인")
    void test1_1() {

        String duplicatedEmail = "testUser@naver.com";

        CreateUserReq createDuplicateUserReq = CreateUserReq.builder()
                .email(duplicatedEmail) // 중복된 이메일
                .nickname("testUser")
                .password("abcd1234!")
                .isMarketing(true)
                .isAlarm(true)
                .build();

        assertThatThrownBy(()-> {
            userService.create(createDuplicateUserReq);
            userRepository.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("유저 생성 실패 - 이미 존재하는 닉네임")
    @Description("유니크 제약조건 확인")
    void test1_2() {

        String duplicatedNickname = "tester";

        CreateUserReq createDuplicateUserReq = CreateUserReq.builder()
                .email("notDupleMail@gmail.com")
                .nickname(duplicatedNickname) // 중복된 닉네임
                .password("abcd1234!")
                .isMarketing(true)
                .isAlarm(true)
                .build();

        assertThatThrownBy(()-> {
            userService.create(createDuplicateUserReq);
            userRepository.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
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
    void test2_1() {
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
    void test2_2() {
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

    @Test
    @DisplayName("닉네임 업데이트 테스트")
    void test3() {
        String updateNickname = "updateNickName";

        User updatedUser = userService.updateNickname(user, updateNickname);

        assertThat(updatedUser.getNickname()).isEqualTo(updateNickname);
    }

    @Test
    @DisplayName("기존과 동일한 닉네임 감지 테스트")
    void test3_1() {
        String duplicateNickname = "tester"; // 기존의 닉네임과 동일한 닉네임

        assertThatThrownBy(() ->
                userService.updateNickname(user, duplicateNickname)
        ).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("중복 닉네임 감지 테스트")
    @Description("유니크 제약조건 확인")
    void test3_2() {
        String duplicateNickname = "tester"; // 이미 존재하는 닉네임

        assertThatThrownBy(() -> {
            userService.updateNickname(anotherUser, duplicateNickname);
            userRepository.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

}
