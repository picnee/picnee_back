package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.review.dto.req.CreateTouristSpotVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.UpdateTouristSpotVoteReviewReq;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.review.entity.ReviewVoteTouristspot;
import com.picnee.travel.domain.review.exception.NotFoundReviewException;
import com.picnee.travel.domain.review.repository.ReviewVoteTouristspotRepository;
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
public class ReviewVoteTouristSpotService {

    private final ReviewVoteTouristspotRepository reviewVoteTouristspotRepository;

    /**
     * 관광지 리뷰 생성
     */
    public void createTouristSpotReview(Review review, CreateTouristSpotVoteReviewReq dto) {
        reviewVoteTouristspotRepository.save(dto.toEntity(review, dto));
    }

    /**
     * 관광지 리뷰 수정
     */
    public void updateTouristSpotReview(Review review, UpdateTouristSpotVoteReviewReq dto) {
        ReviewVoteTouristspot reviewVoteTouristSpot = findById(review.getId());
        reviewVoteTouristSpot.update(dto);
    }

    public ReviewVoteTouristspot findById(UUID reviewId) {
        return reviewVoteTouristspotRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewException(ErrorCode.NOT_FOUND_REVIEW_EXCEPTION));

    }
}
