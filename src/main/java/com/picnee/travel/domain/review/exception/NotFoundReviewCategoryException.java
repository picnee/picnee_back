package com.picnee.travel.domain.review.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundReviewCategoryException extends BusinessException {
    public NotFoundReviewCategoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
