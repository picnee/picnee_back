package com.picnee.travel.domain.user.dto.req;

import com.picnee.travel.domain.user.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateUserReq {

    @Email(message = "이메일 형식이 잘못됐습니다.")
    @NotNull(message = "이메일은 필수입니다.")
    private String email;
    @NotNull(message = "닉네임은 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z가-힣])[A-Za-z\\d가-힣]{2,20}$", message = "닉네임은 2자 이상 20자 이하이며, 영어와 한글이 반드시 하나 이상은 포함되어야 합니다.")
    private String nickname;
    @NotNull(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8자 이상 16자 이하이며, 영문, 숫자, 특수문자로 이루어져야합니다.")
    private String password;
    @NotNull(message = "마케팅 수신여부는 필수 입니다.")
    private Boolean isMarketing;
    @NotNull(message = "알림 수신여부는 필수 입니다.")
    private Boolean isAlarm;
}
