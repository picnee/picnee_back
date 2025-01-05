package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.review.dto.req.CreateRestaurantVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.UpdateRestaurantVoteReviewReq;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.review.exception.NotFoundReviewException;
import com.picnee.travel.domain.review.repository.ReviewVoteRestaurantRepository;
import com.picnee.travel.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewVoteRestaurantService {

    private final ReviewVoteRestaurantRepository reviewVoteRestaurantRepository;

    /**
     * 음식점 투표 리뷰 생성
     * */
    public void createRestaurantReview(Review review, CreateRestaurantVoteReviewReq dto) {
        reviewVoteRestaurantRepository.save(dto.toEntity(review, dto));
    }

    /**
     * 음식점 투표 리뷰 수정
     */
    public void updateRestaurantReview(Review review, UpdateRestaurantVoteReviewReq dto) {
        ReviewVoteRestaurant reviewVoteRestaurant = findById(review.getId());
        reviewVoteRestaurant.update(dto);
    }

    public ReviewVoteRestaurant findById(UUID reviewId) {
        return reviewVoteRestaurantRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewException(ErrorCode.NOT_FOUND_REVIEW_EXCEPTION));

    }
}
