package com.picnee.travel.api;

import com.picnee.travel.api.in.ReviewApi;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.review.service.ReviewService;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import com.picnee.travel.global.security.annotation.AuthenticatedUser;
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

    @GetMapping("/{reviewId}")
    public ResponseEntity<GetReviewRes> getReview(@PathVariable("reviewId") UUID reviewId,
                                                  @AuthenticatedUser AuthenticatedUserReq auth) {

        GetReviewRes res = reviewService.getReview(reviewId, auth);
        return ResponseEntity.status(OK).body(res);
    }


    @GetMapping("/place/{placeId}")
    public ResponseEntity<Page<GetReviewRes>> getReviews(@PathVariable("placeId") String placeId,
                                           @AuthenticatedUser AuthenticatedUserReq auth,
                                           @RequestParam(name = "page", defaultValue = "0") int page) {

        Page<GetReviewRes> res = reviewService.getReviews(placeId, auth, page);
        return ResponseEntity.status(OK).body(res);
    }
}
