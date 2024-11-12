package com.picnee.travel.domain.user.dto.req;

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
public class UpdateUserNicknameReq {
    @NotNull(message = "닉네임은 필수입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z가-힣])[A-Za-z\\d가-힣]{2,20}$", message = "닉네임은 2자 이상 20자 이하이며, 영어와 한글이 반드시 하나 이상은 포함되어야 합니다.")
    private String nickname;
}
