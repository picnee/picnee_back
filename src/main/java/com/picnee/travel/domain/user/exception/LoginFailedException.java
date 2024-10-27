package com.picnee.travel.domain.user.exception;

import com.picnee.travel.global.exception.BusinessMessageException;
import com.picnee.travel.global.exception.ErrorCode;

public class LoginFailedException extends BusinessMessageException {

    public LoginFailedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public LoginFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}