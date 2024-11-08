package com.picnee.travel.domain.notification.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundNotificationException extends BusinessException {

    public NotFoundNotificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
