package com.picnee.travel.api;

import com.picnee.travel.api.in.ReviewApi;
import com.picnee.travel.domain.review.dto.req.CreateRestaurantVoteReviewReq;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.service.ReviewService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;


    @PostMapping("/{placeId}/restaurant")
    public ResponseEntity<String> createRestaurantReview(@Valid @RequestBody CreateRestaurantVoteReviewReq dto,
                                                         @PathVariable("placeId") String placeId,
                                                         @AuthenticatedUser AuthenticatedUserReq auth) {
        Review review = reviewService.createRestaurantReview(dto, placeId, auth);
        return ResponseEntity.status(CREATED).body(review.getId().toString());
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<GetReviewRes> getReview(@PathVariable("reviewId") UUID reviewId,
                                                  @AuthenticatedUser AuthenticatedUserReq auth) {

        GetReviewRes res = reviewService.getReview(reviewId, auth);
        return ResponseEntity.status(OK).body(res);
    }


    @GetMapping("/place/{placeId}")
    public ResponseEntity<Page<GetReviewRes>> getReviews(@PathVariable("placeId") String placeId,
                                                         @AuthenticatedUser AuthenticatedUserReq auth,
                                                         @RequestParam(name = "sort", required = false, defaultValue = "rating") String sort,
                                                         @RequestParam(name = "page", defaultValue = "0") int page) {

        Page<GetReviewRes> res = reviewService.getReviews(placeId, auth, sort, page);
        return ResponseEntity.status(OK).body(res);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") UUID reviewId,
                                             @AuthenticatedUser AuthenticatedUserReq auth) {

        reviewService.deleteReview(reviewId, auth);
        return ResponseEntity.status(OK).build();
    }
}
