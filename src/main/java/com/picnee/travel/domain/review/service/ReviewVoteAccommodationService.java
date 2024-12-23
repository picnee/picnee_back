package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.review.dto.req.CreateAccommodationVoteReviewReq;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.entity.ReviewVoteAccommodation;
import com.picnee.travel.domain.review.repository.ReviewVoteAccommodationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewVoteAccommodationService {

    private final ReviewVoteAccommodationRepository reviewVoteAccommodationRepository;

    public void createAccommodationReview(Review review, CreateAccommodationVoteReviewReq dto) {
        reviewVoteAccommodationRepository.save(dto.toEntity(review, dto));
    }
}
