package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotProvideOAuthException extends BusinessException {
    public NotProvideOAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
