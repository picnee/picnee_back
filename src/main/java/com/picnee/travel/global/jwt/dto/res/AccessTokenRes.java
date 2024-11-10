package com.picnee.travel.global.jwt.dto.res;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccessTokenRes {

    private String grantType;
    private String accessToken;

    public static AccessTokenRes from(String accessToken){
        return AccessTokenRes.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .build();
    }
}
