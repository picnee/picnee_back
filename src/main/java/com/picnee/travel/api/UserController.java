package com.picnee.travel.api;

import com.picnee.travel.api.in.UserApi;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.dto.req.UpdateUserNicknameReq;
import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.service.UserService;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.redis.service.RedisService;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<UserRes> loginUser(@RequestBody LoginUserReq dto, HttpServletResponse response) {
        UserRes res = userService.login(dto, response);
        return ResponseEntity.status(OK).body(res);
    }

    @PostMapping("/reissue")
    public ResponseEntity<AccessTokenRes> reGenerateToken(@AuthenticatedUser AuthenticatedUserReq auth,
                                                          @RequestHeader("RefreshToken") String refreshToken,
                                                          HttpServletResponse response) {
        AccessTokenRes res = userService.reissueToken(auth, refreshToken, response);
        return ResponseEntity.status(OK).body(res);
    }

    @PatchMapping("/nickname")
    public ResponseEntity<String> updateNickname(@AuthenticatedUser AuthenticatedUserReq auth,
                                               @Valid @RequestBody UpdateUserNicknameReq dto) {
        User user = userService.updateNickname(auth, dto.getNickname());
        return ResponseEntity.status(OK).body(user.getNickname());
    }
}

