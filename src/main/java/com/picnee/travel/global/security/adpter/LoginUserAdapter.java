package com.picnee.travel.global.security.adpter;


import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Slf4j
@Getter
public class LoginUserAdapter extends User {

    private final AuthenticatedUserReq user;

    public LoginUserAdapter(AuthenticatedUserReq user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        this.user = user;
    }
}
