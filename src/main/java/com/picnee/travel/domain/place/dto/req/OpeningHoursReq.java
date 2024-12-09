package com.picnee.travel.domain.place.dto.req;

import com.picnee.travel.domain.place.entity.OpeningHours;
import com.picnee.travel.domain.place.entity.Place;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OpeningHoursReq {
    private String day;
    private String time;
    private Place place;

    public static OpeningHours toEntity(OpeningHoursReq dto, Place place) {

        return OpeningHours.builder()
                .day(dto.getDay())
                .time(dto.getTime())
                .place(place)
                .build();
    }
}
