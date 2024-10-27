package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class LoginLockedException extends BusinessException {

    public LoginLockedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
