package com.picnee.travel.domain.place.dto.req;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Period {
    private TimeInfo open;
    private TimeInfo close;
}
