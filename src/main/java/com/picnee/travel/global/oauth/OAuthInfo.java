package com.picnee.travel.global.oauth;

import com.picnee.travel.domain.user.exception.NotProvideOAuthException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static com.picnee.travel.global.exception.ErrorCode.*;

@Getter
@RequiredArgsConstructor
public enum OAuthInfo {
    KAKAO("kakao_account", "properties", "nickname", "email"),
    NAVER("response", null, "nickname", "email"),
    GOOGLE(null, null, "name", "email");

    private final String mainAttribute;
    private final String secondaryAttribute;
    private final String nickname;
    private final String email;

    public String getNickname(Map<String, Object> attributes) {
        return Optional.ofNullable(secondaryAttribute)
                .map(attr -> getAttributeMap(attributes, attr))
                .orElseGet(() -> getAttributeMap(attributes, mainAttribute))
                .getOrDefault(nickname, UUID.randomUUID().toString().substring(0, 20))
                .toString();
    }

    public String getEmail(Map<String, Object> attributes) {
        return Optional.ofNullable(getAttributeMap(attributes, mainAttribute))
                .map(map -> map.get(email))
                .map(Object::toString)
                .orElse("");
    }

    private Map<String, Object> getAttributeMap(Map<String, Object> attributes, String key) {
        return Optional.ofNullable(key)
                .filter(attributes::containsKey)
                .map(k -> (Map<String, Object>) attributes.get(k))
                .orElse(attributes);
    }

    public static OAuthInfo from(String socialName) {
        return Arrays.stream(OAuthInfo.values())
                .filter(oauth -> oauth.name().equalsIgnoreCase(socialName))
                .findFirst()
                .orElseThrow(() -> new NotProvideOAuthException(NOT_PROVIDE_OAUTH_EXCEPTION));
    }
}