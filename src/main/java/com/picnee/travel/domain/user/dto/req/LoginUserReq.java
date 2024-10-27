package com.picnee.travel.domain.user.dto.req;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginUserReq {
    private String email;
    private String password;
}
