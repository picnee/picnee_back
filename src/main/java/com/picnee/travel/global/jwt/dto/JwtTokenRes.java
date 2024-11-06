package com.picnee.travel.global.jwt.dto;

import com.picnee.travel.domain.user.dto.res.UserRes;
import com.picnee.travel.domain.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JwtTokenRes {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private UserRes userRes;

    public static JwtTokenRes from(String accessToken, String refreshToken, User user){
        return JwtTokenRes.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userRes(UserRes.from(user))
                .build();
    }
}
