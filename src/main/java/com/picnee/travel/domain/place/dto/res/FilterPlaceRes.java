package com.picnee.travel.domain.place.dto.res;

import com.picnee.travel.domain.place.entity.PlaceType;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class FilterPlaceRes {

    private String placeId;
    private UUID reviewId;
    private String placeName;
    private PlaceType placeType;
//    private String openingHours;
    private Double reviewRating;
    private Long reviewCount;
    private String mostPopularGoodPoints;
//    private UUID scores;
}
