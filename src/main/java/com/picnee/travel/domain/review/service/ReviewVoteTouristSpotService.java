package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.review.dto.req.CreateTouristspotVoteReviewReq;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.repository.ReviewVoteTouristspotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewVoteTouristSpotService {

    private final ReviewVoteTouristspotRepository reviewVoteTouristspotRepository;


    public void createTouristSpotReview(Review review, CreateTouristspotVoteReviewReq dto) {
        reviewVoteTouristspotRepository.save(dto.toEntity(review, dto));
    }
}
