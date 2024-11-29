package com.picnee.travel.domain.place.dto.res;

import com.picnee.travel.domain.place.dto.req.OpeningHours;
import com.picnee.travel.domain.place.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private OpeningHours openingHours;
    private Double userRatingsTotal;
    private String website;
    private String lat;
    private String lng;

    public static FindPlaceRes from(Place place) {
        return FindPlaceRes.builder()
                .placeId(place.getId())
                .placeName(place.getPlaceName())
                .url(place.getUrl())
                .formattedAddress(place.getFormattedAddress())
                .formattedPhoneNumber(place.getFormattedPhoneNumber())
                .openingHours(null)
                .userRatingsTotal(place.getUserRatingsTotal())
                .website(place.getWebsite())
                .lat(place.getLat())
                .lng(place.getLng())
                .build();
    }
}
