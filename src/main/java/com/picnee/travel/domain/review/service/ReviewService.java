package com.picnee.travel.domain.review.service;

import com.picnee.travel.domain.post.exception.NotFoundPostException;
import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.picnee.travel.global.exception.ErrorCode.NOT_FOUND_POST_EXCEPTION;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review findById(UUID reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NotFoundPostException(NOT_FOUND_POST_EXCEPTION));
    }
}
