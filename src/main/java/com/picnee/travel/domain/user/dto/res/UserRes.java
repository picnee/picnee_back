package com.picnee.travel.domain.user.dto.res;

import com.picnee.travel.domain.user.entity.User;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRes {

    private UUID userId;
    private String nickName;

    public static UserRes from(User user) {
        return UserRes.builder()
                .userId(user.getId())
                .nickName(user.getNickname())
                .build();
    }
}
