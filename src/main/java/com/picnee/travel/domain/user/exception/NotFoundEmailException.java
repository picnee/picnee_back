package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundEmailException extends BusinessException {
    public NotFoundEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}