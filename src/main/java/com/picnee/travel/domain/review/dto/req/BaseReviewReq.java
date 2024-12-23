package com.picnee.travel.domain.review.dto.req;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public abstract class BaseReviewReq {

    private String goodPoints;
    private String lowPoints;
    private String placeTips;
    private Double rating;

    public Review toEntity(BaseReviewReq dto, User user, Place place) {
        return Review.builder()
                .goodPoints(dto.getGoodPoints())
                .lowPoints(dto.getLowPoints())
                .placeTips(dto.getPlaceTips())
                .rating(dto.getRating())
                .user(user)
                .place(place)
                .build();
    }
}