package com.picnee.travel.api.in;


import com.picnee.travel.domain.review.dto.req.*;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.domain.usersReview.dto.req.EvaluateReviewReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

@Tag(name = "revies", description = "review API")
public interface ReviewApi {

    @Operation(summary = "음식점 리뷰 생성", description = "음식점 리뷰를 생성한다.")
    ResponseEntity<String> createRestaurantReview(CreateRestaurantVoteReviewReq dto, String placeId, AuthenticatedUserReq auth);

    @Operation(summary = "관광지 리뷰 생성", description = "관광지 리뷰를 생성한다.")
    ResponseEntity<String> createTouristSpotReview(CreateTouristSpotVoteReviewReq dto, String placeId, AuthenticatedUserReq auth);

    @Operation(summary = "숙소 리뷰 생성", description = "숙소 리뷰를 생성한다.")
    ResponseEntity<String> createAccommodationReview(CreateAccommodationVoteReviewReq dto, String placeId, AuthenticatedUserReq auth);

    @Operation(summary = "음식점 리뷰 수정", description = "음식점 리뷰를 수정한다.")
    ResponseEntity<String> updateRestaurantReview(UpdateRestaurantVoteReviewReq dto, String placeId, UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "관광지 리뷰 수정", description = "관광지 리뷰를 수정한다.")
    ResponseEntity<String> updateTouristSpotReview(UpdateTouristSpotVoteReviewReq dto, String placeId, UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "숙소 리뷰 수정", description = "숙소 리뷰를 수정한다.")
    ResponseEntity<String> updateAccommodationReview(UpdateAccommodationVoteReviewReq dto, String placeId, UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 단건 조회", description =  "리뷰를 단건 조회한다.")
    ResponseEntity<GetReviewRes> getReview(UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 전체 조회", description = "장소에 대한 리뷰를 전체 조회한다.")
    ResponseEntity<Page<GetReviewRes>> getReviews(String placeId, AuthenticatedUserReq auth, String sort, int page);

    @Operation(summary = "리뷰 삭제", description = "장소를 삭제한다.")
    ResponseEntity<Void> deleteReview(UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 평가", description = "리뷰를 평가한다.")
    ResponseEntity<Void> evaluateReview(EvaluateReviewReq dto, String placeId, UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 좋아요", description = "리뷰 좋아요 한다.")
    ResponseEntity<Void> toggleReviewLike(String placeId, UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "베스트 리뷰 조회", description = "해당 장소에 대한 베스트 리뷰를 조회한다.")
    ResponseEntity<List<GetReviewRes>> getPopularReviews(String placeId, AuthenticatedUserReq auth);
}
