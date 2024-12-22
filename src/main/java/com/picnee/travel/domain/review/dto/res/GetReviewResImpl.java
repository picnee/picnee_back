package com.picnee.travel.domain.review.dto.res;


import com.picnee.travel.domain.place.dto.res.PlaceRes;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetReviewResImpl implements GetReviewRes{
    private UUID reviewId;
    private PlaceRes placeRes;
    private String goodPoints;
    private String lowPoints;
    private String placeTips;
    private Double rating;
    private UserRes userRes;
    private LocalDateTime createdAt;

    public static GetReviewResImpl from(Review review) {
        return GetReviewResImpl.builder()
                .reviewId(review.getId())
                .placeRes(PlaceRes.from(review.getPlace()))
                .goodPoints(review.getGoodPoints())
                .lowPoints(review.getLowPoints())
                .placeTips(review.getPlaceTips())
                .rating(review.getRating())
                .userRes(UserRes.from(review.getUser()))
                .createdAt(review.getCreatedAt())
                .build();
    }
}
