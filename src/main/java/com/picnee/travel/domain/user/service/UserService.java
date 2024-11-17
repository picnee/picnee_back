package com.picnee.travel.domain.user.service;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.domain.user.entity.Role;
import com.picnee.travel.domain.user.entity.State;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.*;
import com.picnee.travel.domain.user.repository.UserRepository;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.redis.service.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.picnee.travel.domain.user.entity.State.*;
import static com.picnee.travel.global.exception.ErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RedisService redisService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public void create(CreateUserReq dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .passwordCount(0)
                .accountLock(false)
                .lastPasswordExpired(LocalDateTime.now())
                .profileImage(null)
                .isMarketing(dto.getIsMarketing())
                .isAlarm(dto.getIsAlarm())
                .isDefaultNickname(false)
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();

        userRepository.save(user);
    }

    /**
     * 로그인
     */
    @Transactional(noRollbackFor = LoginFailedException.class)
    public UserRes login(LoginUserReq dto, HttpServletResponse response) {
        User user = findByEmail(dto.getEmail());
        validateUser(user);

        try{
            Authentication authentication = getAuthentication(dto.getEmail(), dto.getPassword());
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);

            createResponseHandler(response, accessToken, refreshToken);

            redisService.saveValue(dto.getEmail(), refreshToken);
            user.resetPasswordCount();
            return UserRes.from(user);
        } catch (BadCredentialsException e) {
            user.failPasswordCount();

            if(user.getPasswordCount() >= 5){
                user.updateLockedStatus();
            }

            throw new LoginFailedException(LOGIN_FAILED_EXCEPTION, "비밀번호 " + user.getPasswordCount());
        } catch (IOException e) {
            log.info("e = {}", e.getMessage());
            throw new IllegalArgumentException("ggg");
        }
    }

    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
    }

    private void createResponseHandler(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        Cookie accessTokenCookie = new Cookie("AccessToken", accessToken);
        Cookie refreshTokenCookie = new Cookie("RefreshToken", refreshToken);

        accessTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setHttpOnly(true);

        // path 설정
        accessTokenCookie.setPath("/");
        refreshTokenCookie.setPath("/");

        // https 통신용
//        accessTokenCookie.setSecure(true);
//        refreshTokenCookie.setSecure(true);

        accessTokenCookie.setMaxAge(24 * 60 * 7);
        refreshTokenCookie.setMaxAge(24 * 60 * 7);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    /**
     * 닉네임 설정
     * */
    @Transactional
    public User updateNickname(AuthenticatedUserReq auth, String nickname) {
        User user = findByEmail(auth.getEmail());

        if(user.getNickname().equals(nickname)){
            throw new IllegalArgumentException("기존 닉네임과 동일한 닉네임은 사용할 수 없습니다.");
        }

        user.updateDefaultNickname(nickname);
        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new NotFoundEmailException(NOT_FOUND_EMAIL_EXCEPTION));
    }

    public void validateUser(User user) {
        if (user.getIsDeleted()){
            throw new NotFoundUserException(NOT_FOUND_USER_EXCEPTION);
        }

        if (user.getState() == LOCKED) {
            throw new LoginLockedException(LOGIN_LOCKED_EXCEPTION);
        }
    }
}
