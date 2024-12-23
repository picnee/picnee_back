package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.review.dto.req.CreateRestaurantVoteReviewReq;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteRestaurant;
import com.picnee.travel.domain.review.repository.ReviewVoteRestaurantRepository;
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

    public void createRestaurantReview(Review review, CreateRestaurantVoteReviewReq dto) {
        reviewVoteRestaurantRepository.save(dto.toEntity(review, dto));
    }

    public ReviewVoteRestaurant findById(UUID reviewId) {
        return reviewVoteRestaurantRepository.findById(reviewId)
                .orElse(null);

    }
}
