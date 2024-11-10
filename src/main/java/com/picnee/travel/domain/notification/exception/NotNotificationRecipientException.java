package com.picnee.travel.domain.notification.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotNotificationRecipientException extends BusinessException {

    public NotNotificationRecipientException(ErrorCode errorCode) {
        super(errorCode);
    }
}