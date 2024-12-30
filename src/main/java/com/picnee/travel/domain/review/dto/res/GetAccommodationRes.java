package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.Review;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetAccommodationRes implements GetReviewRes {

    private GetReviewResImpl restaurantRes;
    private GetAccommodationVoteRes accommodationVoteRes;
    private boolean loginStatus;

    /**
     * 단건 조회
     */
    public static GetAccommodationRes of(Review review) {
        return GetAccommodationRes.builder()
                .restaurantRes(GetReviewResImpl.from(review))
                .accommodationVoteRes(GetAccommodationVoteRes.from(review.getReviewVoteAccommodation()))
                .build();
    }

    /**
     * 인기 순위 조회
     */
    public static List<GetAccommodationRes> list(List<Review> reviews) {
        return reviews.stream()
                .map(GetAccommodationRes::of)
                .toList();
        }

    public static GetReviewRes toReviewRes(GetAccommodationRes res) {
        return res;
    }
}
