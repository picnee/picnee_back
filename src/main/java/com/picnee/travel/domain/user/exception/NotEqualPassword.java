package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotEqualPassword extends BusinessException {
    public NotEqualPassword(ErrorCode errorCode) {
        super(errorCode);
    }
}
