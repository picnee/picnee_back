package com.picnee.travel.domain.place.dto.res;

import com.picnee.travel.domain.place.entity.OpeningHours;
import com.picnee.travel.domain.place.entity.Place;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OpeningHoursRes {
    private String day;
    private String time;

    public static OpeningHoursRes from(OpeningHours dto) {
        return OpeningHoursRes.builder()
                .day(dto.getDay())
                .time(dto.getTime())
                .build();
    }
}
