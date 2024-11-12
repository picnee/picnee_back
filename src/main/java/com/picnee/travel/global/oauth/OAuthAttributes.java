package com.picnee.travel.global.oauth;

import com.picnee.travel.domain.user.entity.Role;
import com.picnee.travel.domain.user.entity.State;
import com.picnee.travel.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Getter
@Builder
@ToString
@AllArgsConstructor
public class OAuthAttributes {

    private Map<String, Object> attributes = new HashMap<>();
    private String email;
    private String nickname;
    private String socialRoot;
    private boolean isDefaultNickname;

    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        OAuthInfo oAuthInfo = OAuthInfo.from(socialName);

        String nickname = oAuthInfo.getNickname(attributes);
        boolean isDefaultNickname = nickname.length() == 21;
        String email = oAuthInfo.getEmail(attributes);

        return OAuthAttributes.builder()
                .nickname(nickname)
                .email(email)
                .attributes(attributes)
                .socialRoot(socialName)
                .isDefaultNickname(isDefaultNickname)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(UUID.randomUUID().toString())
                .role(Role.USER)
                .isDefaultNickname(isDefaultNickname)
                .socialRoot(socialRoot)
                .passwordCount(0)
                .accountLock(false)
                .lastPasswordExpired(LocalDateTime.now())
                .profileImage(null)
                .isMarketing(false)
                .isAlarm(false)
                .state(State.ACTIVE)
                .build();
    }
}

