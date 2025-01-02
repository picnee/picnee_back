package com.picnee.travel.domain.usersReview.service;

import com.picnee.travel.domain.review.entity.Review;
import com.picnee.travel.domain.user.entity.User;
import com.picnee.travel.domain.usersReview.dto.req.EvaluateReviewReq;
import com.picnee.travel.domain.usersReview.entity.UsersReview;
import com.picnee.travel.domain.usersReview.exception.ExistAlreadyReviewException;
import com.picnee.travel.domain.usersReview.exception.SelfReviewEvaluationAndLikeException;
import com.picnee.travel.domain.usersReview.repository.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.picnee.travel.global.exception.ErrorCode.EXISTS_ALREADY_REVIEW_EXCEPTION;
import static com.picnee.travel.global.exception.ErrorCode.SELF_REVIEW_EVALUATION_LIKE_EXCEPTION;

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
        selfEvaluationAndLikeReview(review, user);

        UsersReview usersReview = userReviewRepository.findByUserAndReview(user, review)
                .orElseGet(() -> createUsersReview(user, review));

        // 이미 평가가 되어있다면 exception
        if (usersReview.getGoodAndBad() != null){
            throw new ExistAlreadyReviewException(EXISTS_ALREADY_REVIEW_EXCEPTION);
        }

        // 평가가 되어있지 않을 경우
        if (usersReview.getId() != null) {
            usersReview.updateEvaluation(dto);
            return;
        }

        userReviewRepository.save(EvaluateReviewReq.toEntity(usersReview, dto));
    }

    /**
     * 중간 테이블 리뷰 좋아요 생성
     */
    public boolean incrementLike(Review review, User user) {
        selfEvaluationAndLikeReview(review, user);

        UsersReview usersReview = userReviewRepository.findByUserAndReview(user, review)
                .orElseGet(() -> createUsersReview(user, review));

        boolean likeState = usersReview.getIsLiked() != null && usersReview.getIsLiked();

        usersReview = userReviewRepository.save(usersReview);

        if (!likeState) {
            usersReview.like();
            return likeState;
        }

        usersReview.dislike();
        return likeState;
    }

    private UsersReview createUsersReview(User user, Review review) {
        return UsersReview.builder()
                .user(user)
                .review(review)
                .isLiked(null)
                .goodAndBad(null)
                .build();
    }

    /**
     * 리뷰 평가, 좋아요 자신의 리뷰에는 하지 못하도록 설정
     */
    private static void selfEvaluationAndLikeReview(Review review, User user) {
        if (review.getUser().getId() == user.getId()) {
            throw new SelfReviewEvaluationAndLikeException(SELF_REVIEW_EVALUATION_LIKE_EXCEPTION);
        }
    }
}
