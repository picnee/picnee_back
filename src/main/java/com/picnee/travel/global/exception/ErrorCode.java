package com.picnee.travel.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    /**
     * 400
     */
    BAD_REQUEST_ERROR(BAD_REQUEST, "G001", "Bad Request Exception"),
    INVALID_TYPE_VALUE(BAD_REQUEST, "G002", " Invalid Type Value"),
    IO_ERROR(BAD_REQUEST, "G003", "I/O Exception"),
    JSON_PARSE_ERROR(BAD_REQUEST, "G004", "JsonParseException"),

    /**
     * 401
     */
    LOGIN_FAILED_EXCEPTION(UNAUTHORIZED, "G001", "회 로그인에 실패했습니다. 5회 실패 시 계정이 차단됩니다."),
    LOGIN_LOCKED_EXCEPTION(UNAUTHORIZED, "G002", "비밀번호를 오입력으로 계정이 잠겼습니다. 비밀번호를 변경해주세요."),
    NOT_POST_AUTHOR_EXCEPTION(UNAUTHORIZED, "G003", "게시글의 작성자가 아닙니다. 본인이 작성한 게시글만 수정/삭제가 가능합니다."),
    NOT_LOGIN_EXCEPTION(UNAUTHORIZED, "G004", "로그인이 필요한 서비스입니다."),
    NOT_VALID_REFRESH_TOKEN_EXCEPTION(UNAUTHORIZED, "005", "유효하지 않은 토큰입니다."),

    /**
     * 403
     */
    FORBIDDEN_ERROR(FORBIDDEN, "G001", "Forbidden Exception"),

    /**
     * 404
     */
    NOT_FOUND_ERROR(NOT_FOUND, "G001", "Not Found Exception"),
    NULL_POINT_ERROR(NOT_FOUND, "G002", "Null Point Exception"),
    NOT_FOUND_EMAIL_EXCEPTION(NOT_FOUND, "G003", "존재하지 않는 계정입니다."),
    NOT_FOUND_USER_EXCEPTION(NOT_FOUND, "G004", "존재하지 않는 계정입니다."),
    NOT_FOUND_POST_EXCEPTION(NOT_FOUND, "G005", "존재하지 않는 게시글입니다."),
    NOT_FOUND_BOARD_EXCEPTION(NOT_FOUND, "G005", "존재하지 않는 카테고리입니다."),

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