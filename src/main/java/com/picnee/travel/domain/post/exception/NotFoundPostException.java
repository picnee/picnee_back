package com.picnee.travel.domain.post.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundPostException extends BusinessException {
    public NotFoundPostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
