package com.picnee.travel.domain.token.dto;

import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class CreateOauthToken {

    private String authUUID;
}
