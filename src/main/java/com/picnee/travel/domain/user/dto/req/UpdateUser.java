package com.picnee.travel.domain.user.dto.req;

import com.picnee.travel.domain.user.entity.Gender;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateUser {

    @Pattern(regexp = "^(?=.*[A-Za-z가-힣])[A-Za-z\\d가-힣]{2,20}$", message = "닉네임은 2자 이상 20자 이하이며, 영어와 한글이 반드시 하나 이상은 포함되어야 합니다.")
    private String nickname;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8자 이상 16자 이하이며, 영문, 숫자, 특수문자로 이루어져야합니다.")
    private String oldPassword;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$", message = "비밀번호는 8자 이상 16자 이하이며, 영문, 숫자, 특수문자로 이루어져야합니다.")
    private String newPassword;
    private Boolean isMarketing;
    private Boolean isAlarm;
    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phoneNumber;
    private Gender gender;
    private String birthDate;
}
