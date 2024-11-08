package com.picnee.travel.domain.postComment.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotValidOwnerException extends BusinessException {
    public NotValidOwnerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
