package com.picnee.travel.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.picnee.travel.api.UserController;
import com.picnee.travel.domain.user.dto.req.*;
import com.picnee.travel.domain.user.dto.res.CheckDuplicateRes;
import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.domain.user.entity.Gender;
import com.picnee.travel.domain.user.entity.State;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@WebMvcTest(UserController.class) // UserController 단위테스트
@MockBean(JpaMetamodelMappingContext.class) // jpa동작하지않도록
@AutoConfigureMockMvc(addFilters = false) // 시큐리티 필터 제외
@ActiveProfiles("test")
class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final User mockUser = User.builder()
                .id(UUID.randomUUID())
                .email("mockUser@naver.com")
                .nickname("mockUser")
                .password("abcd1234!")
                .isMarketing(true)
                .isAlarm(true)
                .state(State.ACTIVE)
                .build();

    @Test
    @DisplayName("회원가입 성공 response 테스트")
    void testCreateUserSuccess() throws Exception {
        CreateUserReq createUserReq = CreateUserReq.builder()
                .email("createUser@naver.com")
                .nickname("createUser")
                .password("abcd1234!")
                .isMarketing(true)
                .isAlarm(true)
                .build();
        
        given(userService.create(any(CreateUserReq.class))).willReturn(mockUser);
        
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserReq)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("로그인 성공 response 테스트")
    void testLoginUserSuccess() throws Exception {

        LoginUserReq loginUserReq = LoginUserReq.builder().build();

        UserRes userRes = UserRes.builder()
                .userId(UUID.randomUUID())
                .nickName("TestUser")
                .build();

        JwtTokenRes jwtTokenRes = JwtTokenRes.builder()
                .grantType("Bearer ")
                .accessToken("accessTokenValue")
                .refreshToken("refreshTokenValue")
                .userRes(userRes)
                .build();

        given(userService.login(any(LoginUserReq.class))).willReturn(jwtTokenRes);

        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserReq)))
                .andExpect(status().isOk())
                .andExpect(cookie().value("ACCESS_TOKEN", "accessTokenValue"))
                .andExpect(cookie().value("REFRESH_TOKEN", "refreshTokenValue"));

    }

    @Test
    @DisplayName("닉네임 변경 성공 response 테스트")
    void testUpdateNicknameSuccess() throws Exception {
        String updateNickname = "updateNickname";

        UpdateUserNicknameReq updateUserNicknameReq = UpdateUserNicknameReq.builder()
                .nickname(updateNickname)
                .build();

        User user = User.builder().nickname("mockNickname").build();

        given(userService.updateNickname(any(), any()))
                .willReturn(user);

        mockMvc.perform(patch("/users/nickname")
                        .header("AccessToken", "validAccessToken")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserNicknameReq)))
                .andExpect(status().isOk())
                .andExpect(content().string("mockNickname"));

    }

    //TODO : 여기부터
    @Test
    @DisplayName("내정보 변경 성공 response 테스트")
    void testUpdateUserSuccess() throws Exception {

        UpdateUserReq updateUserReq = UpdateUserReq.builder()
                .nickname("testUser")
                .oldPassword("test!!AA11")
                .newPassword("testChange!!AA11")
                .isMarketing(true)
                .isAlarm(true)
                .phoneNumber("010-0000-0000")
                .gender(Gender.MALE)
                .birthDate("")
                .build();

        given(userService.updateUser(any(), any()))
                .willReturn(mockUser);

        mockMvc.perform(patch("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserReq)))
                .andExpect(status().isOk())
                .andExpect(content().string(mockUser.getId().toString()));

    }

    @Test
    @DisplayName("이메일 중복체크 성공 response 테스트")
    void testEmailDuplicateSuccess() throws Exception {

        CheckDuplicateRes dupRes = CheckDuplicateRes.builder().isExists(true).build();

        given(userService.checkEmailDuplicate(eq("checkDupEmail1234@naver.com")))
                .willReturn(dupRes);

        mockMvc.perform(get("/users/email/exists")
                        .param("email", "checkDupEmail1234@naver.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(true));
    }

    @Test
    @DisplayName("닉네임 중복체크 성공 response 테스트")
    void testNicknameDuplicateSuccess() throws Exception {

        CheckDuplicateRes dupRes = CheckDuplicateRes.builder().isExists(true).build();

        given(userService.checkNicknameDuplicate(eq("dupNickname")))
                .willReturn(dupRes);

        mockMvc.perform(get("/users/nickname/exists")
                        .param("nickname", "dupNickname")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exists").value(true));
    }

}
