package com.picnee.travel.domain.place.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundCategoryException extends BusinessException {
    public NotFoundCategoryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
