package com.picnee.travel.domain.usersReview.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class SelfReviewEvaluationAndLikeException extends BusinessException {
    public SelfReviewEvaluationAndLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
