package com.picnee.travel.global.jwt.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class JwtTokenRes {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    public static JwtTokenRes from(String accessToken, String refreshToken){
        return JwtTokenRes.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
