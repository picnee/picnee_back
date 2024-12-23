package com.picnee.travel.domain.review.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotReviewAuthorException extends BusinessException {
    public NotReviewAuthorException(ErrorCode errorCode) {
        super(errorCode);
    }
}
