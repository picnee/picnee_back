package com.picnee.travel.domain.review.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundReviewException extends BusinessException {
    public NotFoundReviewException(ErrorCode errorCode) {
        super(errorCode);
    }
}