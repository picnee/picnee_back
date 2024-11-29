package com.picnee.travel.domain.place.dto.req;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TimeInfo {
    private Integer day;
    private String time;
    private Integer hours;
    private Integer minutes;
}
