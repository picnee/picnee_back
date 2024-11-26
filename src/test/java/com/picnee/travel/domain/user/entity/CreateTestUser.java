package com.picnee.travel.domain.user.entity;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Component
public class CreateTestUser {

    @Autowired
    UserService userService;

    private CreateUserReq myUser;
    private CreateUserReq anotherUser;

    /**
     * 테스트 계정 생성
     */
    public AuthenticatedUserReq createUser() {
        myUser = CreateUserReq.builder()
                .email("testUser@naver.com")
                .nickname("tester")
                .password("abcd1234!")
                .build();

        userService.create(myUser);

        User user = userService.findByEmail(myUser.getEmail());

        return AuthenticatedUserReq.of(user);
    }

    /**
     * 토큰 요청
     */
    public JwtTokenRes createJwtToken() {
        LoginUserReq loginUserReq = LoginUserReq.builder()
                .email(myUser.getEmail())
                .password(myUser.getPassword())
                .build();

        return userService.login(loginUserReq);
    }

    /**
     * 다른 유저 생성
     */
    public AuthenticatedUserReq createAnotherUser() {
        anotherUser = CreateUserReq.builder()
                .email("testAnother@naver.com")
                .nickname("another")
                .password("abcd1234!")
                .build();

        userService.create(anotherUser);

        User user = userService.findByEmail(anotherUser.getEmail());

        return AuthenticatedUserReq.of(user);
    }
}
