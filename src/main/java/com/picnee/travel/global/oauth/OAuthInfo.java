package com.picnee.travel.global.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public enum OAuthInfo {
    KAKAO("kakao_account", "properties", "nickname", "email"),
    NAVER("response", null, "nickname", "email"),
    GOOGLE(null, null, "name", "email");

    private final String mainAttributeKey;
    private final String secondaryAttributeKey;
    private final String nicknameKey;
    private final String emailKey;

    public String getNickname(Map<String, Object> attributes) {
        Map<String, Object> primary = getAttributeMap(attributes, mainAttributeKey);
        Map<String, Object> secondary = secondaryAttributeKey != null ? getAttributeMap(attributes, secondaryAttributeKey) : primary;
        return secondary.get(nicknameKey) != null ? secondary.get(nicknameKey).toString() : UUID.randomUUID().toString().substring(0, 21);
    }

    public String getEmail(Map<String, Object> attributes) {
        Map<String, Object> primary = getAttributeMap(attributes, mainAttributeKey);
        return primary.get(emailKey).toString();
    }

    private Map<String, Object> getAttributeMap(Map<String, Object> attributes, String key) {
        return key != null && attributes.containsKey(key) ? (Map<String, Object>) attributes.get(key) : attributes;
    }

    public static OAuthInfo from(String provider) {
        return Arrays.stream(OAuthInfo.values())
                .filter(item -> item.name().equalsIgnoreCase(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid provider: " + provider));
    }
}