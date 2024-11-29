package com.picnee.travel.domain.place.dto.req;

import com.picnee.travel.domain.place.entity.Place;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CreatePlaceReq {

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

    public static Place toEntity(CreatePlaceReq dto) {
        return Place.builder()
                .id(dto.getPlaceId())
                .placeName(dto.getPlaceName())
                .url(dto.getUrl())
                .formattedAddress(dto.getFormattedAddress())
                .formattedPhoneNumber(dto.getFormattedPhoneNumber())
                .openingHours(null)
                .userRatingsTotal(dto.getUserRatingsTotal())
                .website(dto.getWebsite())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .build();
    }
}
