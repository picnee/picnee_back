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
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest); // OAuth2 정보 가져오기

        Map<String, Object> originAttr = oAuth2User.getAttributes();
        String social = userRequest.getClientRegistration().getRegistrationId();
        OAuthAttributes attributes = OAuthAttributes.of(social, originAttr);

        User user = getOrCreateUser(attributes);
        String email = user.getEmail();

        List<GrantedAuthority> authorities = getAuthorities(user);

        return new OAuth2CustomUser(email, social, originAttr, authorities, user.isDefaultNickname(), attributes);
    }

    private User getOrCreateUser(OAuthAttributes oAuth2User) {
        Optional<User> existEmail = userRepository.findByEmail(oAuth2User.getEmail());

        if (existEmail.isPresent()) {
            return existEmail.get();
        }

        User user = oAuth2User.toEntity();
        return userRepository.save(user);
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        return Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );
    }
}
