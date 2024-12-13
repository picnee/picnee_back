package com.picnee.travel.domain.post.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotAuthException extends BusinessException {
    public NotAuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
