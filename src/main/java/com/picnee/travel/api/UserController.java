package com.picnee.travel.api;

import com.picnee.travel.api.in.UserApi;
import com.picnee.travel.domain.user.dto.req.*;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.dto.req.UpdateUserNicknameReq;
import com.picnee.travel.domain.user.dto.res.CheckDuplicateRes;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.redis.service.RedisService;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final RedisService redisService;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserReq dto) {
        userService.create(dto);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenRes> loginUser(@RequestBody LoginUserReq dto, HttpServletResponse response) {
        JwtTokenRes res = userService.login(dto);

        ResponseCookie accessTokenCookie = ResponseCookie.from("ACCESS_TOKEN", res.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60)
                .sameSite("None")
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("REFRESH_TOKEN", res.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 7)
                .sameSite("None")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return ResponseEntity.status(OK).body(res);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<String> updateNickname(@AuthenticatedUser AuthenticatedUserReq auth,
                                               @Valid @RequestBody UpdateUserNicknameReq dto) {
        User user = userService.updateNickname(auth, dto.getNickname());
        return ResponseEntity.status(OK).body(user.getNickname());
    }

    @PatchMapping
    public ResponseEntity<String> updateUser(@AuthenticatedUser AuthenticatedUserReq auth,
                                             @Valid @RequestBody UpdateUserReq dto) {
        User user = userService.updateUser(auth, dto);
        return ResponseEntity.status(OK).body(user.getId().toString());
    }
  
    @GetMapping("/email/exists")
    public ResponseEntity<CheckDuplicateRes> checkEmailDuplicate(@RequestParam("email") String email) {
        CheckDuplicateRes res = userService.checkEmailDuplicate(email);
        return ResponseEntity.status(OK).body(res);
    }

    @GetMapping("/nickname/exists")
    public ResponseEntity<CheckDuplicateRes> checkNicknameDuplicate(@RequestParam("nickname") String nickname) {
        CheckDuplicateRes res = userService.checkNicknameDuplicate(nickname);
        return ResponseEntity.status(OK).body(res);
    }
}

