package com.picnee.travel.domain.postComment.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundCommentException extends BusinessException {
    public NotFoundCommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
