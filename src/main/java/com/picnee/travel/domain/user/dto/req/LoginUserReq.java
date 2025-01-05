package com.picnee.travel.domain.user.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class LoginUserReq {
    @NotNull(message = "이메일은 필수입니다.")
    private String email;
    @NotNull(message = "비밀번호는 필수입니다.")
    private String password;
}
