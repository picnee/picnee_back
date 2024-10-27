package com.picnee.travel.global.oauth;

import com.picnee.travel.domain.user.entity.Role;
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

        return OAuthAttributes.builder()
                .nickname(String.valueOf(oauthResponse.get("nickname")))
                .email(String.valueOf(oauthResponse.get("email")))
                .attributes(attributes)
                .nameAttributesKey(response)
                .build();
    }

    private static OAuthAttributes ofGoogle(String id, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nickname(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributesKey(id)
                .build();
    }

    private static OAuthAttributes ofKakao(String id, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return OAuthAttributes.builder()
                .nickname(String.valueOf(properties.get("nickname")))
                .email(String.valueOf(response.get("email")))
                .attributes(response)
                .nameAttributesKey(id)
                .build();
    }
}

