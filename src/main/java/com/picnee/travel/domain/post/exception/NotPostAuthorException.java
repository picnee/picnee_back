package com.picnee.travel.domain.post.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotPostAuthorException extends BusinessException {
    public NotPostAuthorException(ErrorCode errorCode) {
        super(errorCode);
    }
}