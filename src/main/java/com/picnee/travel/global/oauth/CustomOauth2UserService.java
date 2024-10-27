package com.picnee.travel.global.oauth;

import com.picnee.travel.domain.user.entity.Role;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2CustomUser loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        Map<String, Object> originAttr = oAuth2User.getAttributes();
        String social = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(social, originAttr);

        // 사용자 정보 조회
        Optional<User> existEmail = userRepository.findByEmail(attributes.getEmail());
        boolean isNewUser = existEmail.isEmpty();

        // 기존 사용자이거나, 신규 사용자를 위한 정보를 담은 OAuth2CustomUser 생성
        List<GrantedAuthority> authorities = existEmail.isPresent()
                ? getAuthorities(existEmail.get())
                : Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name()));

        return new OAuth2CustomUser(
                attributes.getEmail(),
                social,
                originAttr,
                authorities,
                isNewUser,
                isNewUser ? attributes : null
        );
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );
    }
}
