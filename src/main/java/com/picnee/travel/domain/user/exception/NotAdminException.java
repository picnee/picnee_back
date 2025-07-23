package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotAdminException extends BusinessException {
    public NotAdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}
