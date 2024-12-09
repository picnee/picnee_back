package com.picnee.travel.domain.postComment.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotProvideCommentLikeException extends BusinessException {
    public NotProvideCommentLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
