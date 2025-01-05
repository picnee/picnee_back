package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.review.dto.req.CreateAccommodationVoteReviewReq;
import com.picnee.travel.domain.review.dto.req.UpdateAccommodationVoteReviewReq;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.review.exception.NotFoundReviewException;
import com.picnee.travel.domain.review.repository.ReviewVoteAccommodationRepository;
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
public class ReviewVoteAccommodationService {

    private final ReviewVoteAccommodationRepository reviewVoteAccommodationRepository;

    public void createAccommodationReview(Review review, CreateAccommodationVoteReviewReq dto) {
        reviewVoteAccommodationRepository.save(dto.toEntity(review, dto));
    }

    public void updateAccommodationReview(Review review, UpdateAccommodationVoteReviewReq dto) {
        ReviewVoteAccommodation reviewVoteAccommodation = findById(review.getId());
        reviewVoteAccommodation.update(dto);
    }

    public ReviewVoteAccommodation findById(UUID reviewId) {
        return reviewVoteAccommodationRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundReviewException(ErrorCode.NOT_FOUND_REVIEW_EXCEPTION));

    }
}
