package com.picnee.travel.domain.user.dto.req;

import com.picnee.travel.domain.user.entity.Role;
import com.picnee.travel.domain.user.entity.User;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthenticatedUserReq {
    private UUID id;
    private String email;
    private String password;
    private Role role;

    public static AuthenticatedUserReq of(User user) {
        return AuthenticatedUserReq.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}
