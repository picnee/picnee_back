package com.picnee.travel.api.in;


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

    @Operation(summary = "리뷰 단건 조회", description =  "리뷰를 단건 조회한다.")
    ResponseEntity<GetReviewRes> getReview(UUID reviewId, AuthenticatedUserReq auth);

    @Operation(summary = "리뷰 전체 조회", description = "장소에 대한 리뷰를 전체조회한다.")
    ResponseEntity<Page<GetReviewRes>> getReviews(String placeId, AuthenticatedUserReq auth, int page);
}
