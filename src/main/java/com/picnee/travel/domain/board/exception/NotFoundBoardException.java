package com.picnee.travel.domain.board.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundBoardException extends BusinessException {
    public NotFoundBoardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
