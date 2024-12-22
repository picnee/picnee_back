package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.place.dto.res.PlaceRes;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.user.dto.res.UserRes;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GetRestaurantRes implements GetReviewRes{

    private GetReviewResImpl restaurantRes;
    private GetRestaurantVoteRes restaurantVoteRes;
    private boolean loginStatus;

    /**
     * 단건 조회
     */
    public static GetRestaurantRes of(Review review, ReviewVoteRestaurant reviewVoteRestaurant, AuthenticatedUserReq auth) {
        return GetRestaurantRes.builder()
                .restaurantRes(GetReviewResImpl.from(review))
                .restaurantVoteRes(GetRestaurantVoteRes.from(reviewVoteRestaurant))
                .loginStatus(auth != null)
                .build();
    }

    /**
     * 페이징
     */
//    public static GetRestaurantRes from(Review review) {
//        return GetRestaurantRes.builder()
//                .reviewId(review.getId())
//                .placeRes(PlaceRes.from(review.getPlace()))
//                .userRes(UserRes.from(review.getUser()))
//                .build();
//    }

//    public static Page<GetRestaurantRes> paging(Page<Review> reviews) {
//        List<GetRestaurantRes> contents = reviews.getContent().stream()
//                .map(GetRestaurantRes::from)
//                .collect(Collectors.toList());
//
//        return new PageImpl<>(contents, reviews.getPageable(), reviews.getTotalElements());
//    }
}
