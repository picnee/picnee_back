package com.picnee.travel.domain.user.service;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.entity.Role;
import com.picnee.travel.domain.user.entity.State;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.*;
import com.picnee.travel.domain.user.repository.UserRepository;
import com.picnee.travel.global.jwt.dto.res.AccessTokenRes;
import com.picnee.travel.global.jwt.dto.res.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        //TODO : phoneNumber 인코딩 후 저장
        String phoneNumber = dto.getPhoneNumber().replaceAll("-", "");

        User user = User.builder()
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(phoneNumber)
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .socialRoot(dto.getSocial() == null ? null : dto.getSocial())
                .passwordCount(0)
                .accountLock(false)
                .lastPasswordExpired(LocalDateTime.now())
                .profileImage(null)
                .isMarketing(dto.getIsMarketing())
                .isAlarm(dto.getIsAlarm())
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();

        userRepository.save(user);
    }

    /**
     * 로그인
     */
    @Transactional(noRollbackFor = LoginFailedException.class)
    public JwtTokenRes login(LoginUserReq dto) {
        User user = findByEmail(dto.getEmail());
        validateUser(user);

        try{
            Authentication authentication = getAuthentication(dto.getEmail(), dto.getPassword());
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);

            redisService.saveValue(dto.getEmail(), refreshToken);
            user.resetPasswordCount();
            return JwtTokenRes.from(accessToken, refreshToken, user);
        } catch (BadCredentialsException e) {
            user.failPasswordCount();

            if(user.getPasswordCount() >= 5){
                user.changeLockedStatus();
            }

            throw new LoginFailedException(LOGIN_FAILED_EXCEPTION, "비밀번호 " + user.getPasswordCount());
        }
    }

    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
    }

    /**
     * accessToken 재발급
     */
    public AccessTokenRes reissueToken(AuthenticatedUserReq auth, String refreshToken) {
        String token = redisService.getValue(auth.getEmail());

        if(refreshToken.equals(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            String accessToken = tokenProvider.generateAccessToken(authentication);
            return AccessTokenRes.from(accessToken);
        }

        throw new NotValidRefreshTokenException(NOT_VALID_REFRESH_TOKEN_EXCEPTION);

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
