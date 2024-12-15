package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.place.entity.Place;
import com.picnee.travel.domain.place.service.PlaceService;
import com.picnee.travel.domain.post.exception.NotFoundPostException;
import com.picnee.travel.domain.post.service.PostService;
import com.picnee.travel.domain.review.dto.res.GetReviewRes;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.exception.NotFoundReviewException;
import com.picnee.travel.domain.review.repository.ReviewRepository;
import com.picnee.travel.domain.user.dto.req.AuthenticatedUserReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.NOT_FOUND_POST_EXCEPTION;
import static com.picnee.travel.global.exception.ErrorCode.NOT_FOUND_REVIEW_EXCEPTION;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PlaceService placeService;

    // TODO 리뷰 생성
    // 필수 필터링 값 받아서(흡연 여부 등) 생성 되어야 함

    /**
     * 리뷰 조회
     */
    public GetReviewRes getReview(UUID reviewId, AuthenticatedUserReq auth) {
        Review review = findByIdNotDeletedReview(reviewId);
        return GetReviewRes.of(review, auth);
    }

    /**
     * 한 장소에 대한 리뷰 전체 조회
     */
    public Page<GetReviewRes> getReviews(String placeId, AuthenticatedUserReq auth, int page) {
        Place place = placeService.findById(placeId);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Review> review = reviewRepository.findByReviewOfPlace(place, pageable);
        return GetReviewRes.paging(review);
    }

    /**
     * 게시글이 삭제됐다면 exception 발생
     */
    public Review findByIdNotDeletedReview(UUID reviewId) {
        Review review = findById(reviewId);

        if (review.getIsDeleted()) {
            throw new NotFoundReviewException(NOT_FOUND_REVIEW_EXCEPTION);
        }

        return review;
    }

    // TODO 리뷰 수정
    public Review findById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST_EXCEPTION));
    }
}
