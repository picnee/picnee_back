package com.picnee.travel.api.in;


import com.picnee.travel.domain.review.dto.req.CreateAccommodationVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.CreateRestaurantVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.CreateTouristspotVoteReviewReq;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@Tag(name = "posts/comments", description = "postComment API")
public interface ReviewApi {

    @Operation(summary = "음식점 리뷰 생성", description = "음식점 리뷰를 생성한다.")
    ResponseEntity<String> createRestaurantReview(CreateRestaurantVoteReviewReq dto, String placeId, AuthenticatedUserReq auth);

    @Operation(summary = "관광지 리뷰 생성", description = "관광지 리뷰를 생성한다.")
    ResponseEntity<String> createTouristSpotReview(CreateTouristspotVoteReviewReq dto, String placeId, AuthenticatedUserReq auth);

    @Operation(summary = "숙소 리뷰 생성", description = "숙소 리뷰를 생성한다.")
    ResponseEntity<String> createAccommodationReview(CreateAccommodationVoteReviewReq dto, String placeId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 단건 조회", description =  "리뷰를 단건 조회한다.")
    ResponseEntity<GetReviewRes> getReview(UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 전체 조회", description = "장소에 대한 리뷰를 전체조회한다.")
    ResponseEntity<Page<GetReviewRes>> getReviews(String placeId, AuthenticatedUserReq auth, String sort, int page);

    @Operation(summary = "리뷰 삭제", description = "장소를 삭제한다.")
    ResponseEntity<Void> deleteReview(UUID reviewId, AuthenticatedUserReq auth);
}
