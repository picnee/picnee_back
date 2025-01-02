package com.picnee.travel.domain.place.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundPlaceException extends BusinessException {
    public NotFoundPlaceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
