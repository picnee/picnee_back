package com.picnee.travel.domain.report.exception;

import com.picnee.travel.global.exception.BusinessException;
import com.picnee.travel.global.exception.ErrorCode;

public class NotFoundReportException extends BusinessException {
    public NotFoundReportException(ErrorCode errorCode) {
        super(errorCode);
    }
}
