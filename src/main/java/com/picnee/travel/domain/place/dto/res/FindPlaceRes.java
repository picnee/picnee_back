package com.picnee.travel.domain.place.dto.res;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.entity.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class FindPlaceRes {

    private String placeId;
    private String placeName;
    private String url;
    private String formattedAddress;
    private String formattedPhoneNumber;
    private Double rating;
    private String website;
    private String lat;
    private String lng;
    private PlaceType type;
    private List<OpeningHoursRes> openingHoursRes;

    public static FindPlaceRes from(Place place) {
        return FindPlaceRes.builder()
                .placeId(place.getId())
                .placeName(place.getPlaceName())
                .url(place.getUrl())
                .formattedAddress(place.getFormattedAddress())
                .formattedPhoneNumber(place.getFormattedPhoneNumber())
                .rating(place.getRating())
                .openingHoursRes(place.getOpeningHours().stream()
                                .map(OpeningHoursRes::from)
                                .collect(Collectors.toList())
                )
                .website(place.getWebsite())
                .lat(place.getLat())
                .lng(place.getLng())
                .type(place.getTypes())
                .build();
    }
}
