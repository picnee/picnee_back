package com.picnee.travel.domain.user.dto.res;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CheckDuplicateRes {
    private boolean isExists;
}
