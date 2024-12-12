package com.picnee.travel.domain.place.dto.req;

import com.picnee.travel.domain.place.dto.PlaceTypeWeight;
import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.entity.PlaceType;
import lombok.*;

import java.util.List;
import java.util.Map;

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
    private Double rating;
    private String website;
    private String lat;
    private String lng;
    private List<String> types;
    private List<OpeningHoursReq> openingHoursList;
    

    public static Place toEntity(CreatePlaceReq dto) {
        PlaceType type = PlaceTypeWeight.filterType(dto.getTypes());

        return Place.builder()
                .id(dto.getPlaceId())
                .placeName(dto.getPlaceName())
                .url(dto.getUrl())
                .formattedAddress(dto.getFormattedAddress())
                .formattedPhoneNumber(dto.getFormattedPhoneNumber())
                .rating(dto.getRating())
                .website(dto.getWebsite())
                .lat(dto.getLat())
                .lng(dto.getLng())
                .types(type)
                .build();
    }
}
