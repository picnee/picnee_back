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
    private String nameAttributesKey;
    private String email;
    private String nickname;
    private boolean isDefaultNickname;

    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        if("kakao".equalsIgnoreCase(socialName)) {
            return ofKakao("id", attributes);
        } else if("google".equalsIgnoreCase(socialName)) {
            return ofGoogle("id", attributes);
        } else if("naver".equalsIgnoreCase(socialName)) {
            return ofNaver("response", attributes);
        }

        throw new IllegalArgumentException("잘못된 소셜 정보입니다.");
    }

    private static OAuthAttributes ofNaver(String response, Map<String, Object> attributes) {
        Map<String, Object> oauthResponse = (Map<String, Object>) attributes.get("response");

        // TODO 중복 로직 메서드로 만들기
        boolean isDefaultNickname = oauthResponse.get("nickname") == null;
        String nickname = isDefaultNickname ? UUID.randomUUID().toString().substring(0, 21) : String.valueOf(oauthResponse.get("nickname"));

        return OAuthAttributes.builder()
                .nickname(nickname)
                .email(String.valueOf(oauthResponse.get("email")))
                .attributes(attributes)
                .nameAttributesKey(response)
                .isDefaultNickname(isDefaultNickname)
                .build();
    }

    private static OAuthAttributes ofGoogle(String id, Map<String, Object> attributes) {
        boolean isDefaultNickname = attributes.get("name") == null;
        String nickname = isDefaultNickname? UUID.randomUUID().toString().substring(0, 21) : String.valueOf(attributes.get("name"));

        return OAuthAttributes.builder()
                .nickname(nickname)
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributesKey(id)
                .isDefaultNickname(isDefaultNickname)
                .build();
    }

    private static OAuthAttributes ofKakao(String id, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        boolean isDefaultNickname = properties == null || properties.get("nickname") == null;
        String nickname = isDefaultNickname ? UUID.randomUUID().toString().substring(0, 21) : String.valueOf(properties.get("nickname"));

        return OAuthAttributes.builder()
                .nickname(nickname)
                .email(String.valueOf(response.get("email")))
                .attributes(response)
                .nameAttributesKey(id)
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
//                .socialRoot(dto.getSocial() == null ? null : dto.getSocial()) TODO 어떻게 받아올지 생각하기
                .passwordCount(0)
                .accountLock(false)
                .lastPasswordExpired(LocalDateTime.now())
                .profileImage(null)
                .isMarketing(false)
                .isAlarm(false)
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();
    }
}

