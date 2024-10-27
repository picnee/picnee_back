package com.picnee.travel.domain.user.service;

import com.picnee.travel.domain.user.dto.req.CreateUserReq;
import com.picnee.travel.domain.user.dto.req.LoginUserReq;
import com.picnee.travel.domain.user.entity.Role;
import com.picnee.travel.domain.user.entity.State;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.repository.UserRepository;
import com.picnee.travel.global.jwt.dto.JwtTokenRes;
import com.picnee.travel.global.jwt.provider.TokenProvider;
import com.picnee.travel.global.redis.service.RedisService;
import jakarta.persistence.criteria.CriteriaBuilder;
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
                .username(dto.getUsername())
                .nickname(dto.getNickname())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(phoneNumber)
                .gender(dto.getGender())
                .birthDate(dto.getBirthDate())
                .socialRoot(null)
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
    @Transactional(noRollbackFor = IllegalAccessError.class)
    public JwtTokenRes login(LoginUserReq dto) {
        User user = findByEmail(dto.getEmail());
        validateUser(user);

        try{
            Authentication authentication = getAuthentication(dto.getEmail(), dto.getPassword());
            String accessToken = tokenProvider.generateAccessToken(authentication);
            String refreshToken = tokenProvider.generateRefreshToken(authentication);

            redisService.saveValue(dto.getEmail(), refreshToken);
            user.resetPasswordCount();
            return JwtTokenRes.from(accessToken, refreshToken);
        } catch (BadCredentialsException e) {
            user.failPasswordCount();

            if(user.getPasswordCount() >= 5){
                user.changeLockedStatus();
            }

            throw new IllegalArgumentException("하하하하호호호");
        }
    }

    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new IllegalArgumentException("NOT_FOUND_EMAIL_EXCEPTION"));
    }

    public void validateUser(User user) {
        if (user.getIsDeleted()){
            throw new IllegalArgumentException("INVALID_USER_EXCEPTION");
        }

        if (user.getState() == LOCKED) {
            throw new IllegalArgumentException();
        }
    }
}
