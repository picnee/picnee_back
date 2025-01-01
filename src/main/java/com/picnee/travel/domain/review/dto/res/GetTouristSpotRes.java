package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.Review;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetTouristSpotRes implements GetReviewRes{

    private GetReviewResImpl touristSpotRes;
    private GetTouristSptVoteRes touristSptVoteRes;
    private boolean loginStatus;

    public static GetTouristSpotRes of(Review review) {
        return GetTouristSpotRes.builder()
                .touristSpotRes(GetReviewResImpl.from(review))
                .touristSptVoteRes(GetTouristSptVoteRes.from(review.getReviewVoteTouristspot()))
                .build();
    }
}
