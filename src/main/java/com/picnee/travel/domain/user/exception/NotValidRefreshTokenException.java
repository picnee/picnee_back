package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotValidRefreshTokenException extends BusinessException {
    public NotValidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
