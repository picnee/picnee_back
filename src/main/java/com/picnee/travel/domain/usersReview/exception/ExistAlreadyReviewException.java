package com.picnee.travel.domain.usersReview.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class ExistAlreadyReviewException extends BusinessException {
    public ExistAlreadyReviewException(ErrorCode errorCode) {
        super(errorCode);
    }
}
