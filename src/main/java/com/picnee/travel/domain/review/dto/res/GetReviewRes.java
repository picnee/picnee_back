package com.picnee.travel.domain.review.dto.res;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;

public interface GetReviewRes {

    //여행지
    static GetReviewRes of(Review review, ReviewVoteRestaurant reviewVoteRestaurant, AuthenticatedUserReq auth) {
        return GetRestaurantRes.of(review, reviewVoteRestaurant, auth);
    }

//    //관광지
//    public static GetReviewRes of(Review review, GetRestaurantVoteRes res, AuthenticatedUserReq auth) {
//
//        return null;
//    }
//
//    //숙소
//    public static GetReviewRes of(Review review, GetRestaurantVoteRes res, AuthenticatedUserReq auth) {
//
//        return null;
//    }

}
