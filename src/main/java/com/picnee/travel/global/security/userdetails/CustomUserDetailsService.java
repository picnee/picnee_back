package com.picnee.travel.global.security.userdetails;

import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.user.exception.NotFoundEmailException;
import com.picnee.travel.domain.user.repository.UserRepository;
import com.picnee.travel.global.exception.ErrorCode;
import com.picnee.travel.global.security.adpter.LoginUserAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

import static com.picnee.travel.global.exception.ErrorCode.NOT_FOUND_EMAIL_EXCEPTION;

@Slf4j
@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new NotFoundEmailException(NOT_FOUND_EMAIL_EXCEPTION));
    }

    private UserDetails createUserDetails(User user) {
        Collection<? extends GrantedAuthority> authorities = getAuthorities(user);
        return new LoginUserAdapter(AuthenticatedUserReq.of(user), authorities);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }
}
