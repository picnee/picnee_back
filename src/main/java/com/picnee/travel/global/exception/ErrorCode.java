package com.picnee.travel.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    /**
     * 400
     */
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "G001", "Bad Request Exception"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "G002", " Invalid Type Value"),
    IO_ERROR(HttpStatus.BAD_REQUEST, "G003", "I/O Exception"),
    JSON_PARSE_ERROR(HttpStatus.BAD_REQUEST, "G004", "JsonParseException"),

    /**
     * 401
     */
    LOGIN_FAILED_EXCEPTION(HttpStatus.UNAUTHORIZED, "G001", "회 로그인에 실패했습니다. 5회 실패 시 계정이 차단됩니다."),
    LOGIN_LOCKED_EXCEPTION(HttpStatus.UNAUTHORIZED, "G002", "비밀번호를 오입력으로 계정이 잠겼습니다. 비밀번호를 변경해주세요."),
    NOT_LOGIN_EXCEPTION(HttpStatus.UNAUTHORIZED, "G004", "로그인이 필요한 서비스입니다."),

    /**
     * 403
     */
    FORBIDDEN_ERROR(HttpStatus.FORBIDDEN, "G001", "Forbidden Exception"),

    /**
     * 404
     */
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "G001", "Not Found Exception"),
    NULL_POINT_ERROR(HttpStatus.NOT_FOUND, "G002", "Null Point Exception"),
    NOT_FOUND_EMAIL_EXCEPTION(HttpStatus.NOT_FOUND, "G003", "존재하지 않는 계정입니다."),
    NOT_FOUND_USER_EXCEPTION(HttpStatus.NOT_FOUND, "G004", "존재하지 않는 계정입니다."),

    /**
     * 500
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G001", "Internal Server Error Exception");


    private final HttpStatus status;
    private final String divisionCode;
    private final String message;

    ErrorCode(final HttpStatus status, final String divisionCode, final String message) {
        this.status = status;
        this.divisionCode = divisionCode;
        this.message = message;
    }
}