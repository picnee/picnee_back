package com.picnee.travel.domain.place.dto.res;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.entity.PlaceType;
import lombok.*;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class PlaceRes {

    private String placeId;
    private String placeName;
    private String lat;
    private String lng;

    public static PlaceRes from(Place place) {
        return PlaceRes.builder()
                .placeId(place.getId())
                .placeName(place.getPlaceName())
                .lat(place.getLat())
                .lng(place.getLng())
                .build();

    }
}
