package com.picnee.travel.domain.usersReview.service;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.userPostCommet.entity.UserPostComment;
import com.picnee.travel.domain.usersReview.dto.req.EvaluateReviewReq;
import com.picnee.travel.domain.usersReview.entity.UsersReview;
import com.picnee.travel.domain.usersReview.exception.ExistAlreadyReviewException;
import com.picnee.travel.domain.usersReview.repository.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.picnee.travel.global.exception.ErrorCode.EXISTS_ALREADY_REVIEW_EXCEPTION;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;

    /**
     * 중간 테이블에 리뷰 생성
     */
    public void evaluateReview(EvaluateReviewReq dto, Review review, User user) {
        if (userReviewRepository.findByUserAndReview(user, review).isPresent()) {
            throw new ExistAlreadyReviewException(EXISTS_ALREADY_REVIEW_EXCEPTION);
        }

        userReviewRepository.save(EvaluateReviewReq.toEntity(dto, review, user));
    }
}
