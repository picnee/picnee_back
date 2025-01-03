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
    NOT_EQUAL_PASSWORD_EXCEPTION(BAD_REQUEST, "G005", "비밀번호가 일치하지 않습니다."),

    /**
     * 401
     */
    LOGIN_FAILED_EXCEPTION(UNAUTHORIZED, "G001", "회 로그인에 실패했습니다. 5회 실패 시 계정이 차단됩니다."),
    LOGIN_LOCKED_EXCEPTION(UNAUTHORIZED, "G002", "비밀번호를 오입력으로 계정이 잠겼습니다. 비밀번호를 변경해주세요."),
    NOT_POST_AUTHOR_EXCEPTION(UNAUTHORIZED, "G003", "게시글의 작성자가 아닙니다. 본인이 작성한 게시글만 수정/삭제가 가능합니다."),
    NOT_LOGIN_EXCEPTION(UNAUTHORIZED, "G004", "로그인이 필요한 서비스입니다."),
    NOT_VALID_REFRESH_TOKEN_EXCEPTION(UNAUTHORIZED, "005", "유효하지 않은 토큰입니다."),
    NOT_VALID_OWNER_EXCEPTION(UNAUTHORIZED, "G006", "작성자만 삭제/수정이 가능합니다."),
    NOT_NOTIFICATION_RECIPIENT_EXCEPTION(UNAUTHORIZED, "G007", "알림 수신자가 아닙니다. 본인의 알림만 읽을 수 있습니다."),
    NOT_PROVIDE_OAUTH_EXCEPTION(UNAUTHORIZED, "G008", "올바르지 않은 소셜 로그인입니다."),
    NOT_PROVIDE_COMMENT_LIKE_EXCEPTION(UNAUTHORIZED, "G009", "댓글 좋아요는 로그인시 가능합니다."),
    NOT_AUTH_EXCEPTION(UNAUTHORIZED, "G010", "로그인한 유저만 접근가능합니다."),
    NOT_REVIEW_AUTHOR_EXCEPTION(UNAUTHORIZED, "G011", "리뷰의 작성자가 아닙니다. 본인이 작성한 리뷰만 수정/삭제가 가능합니다."),
    EXISTS_ALREADY_REVIEW_EXCEPTION(UNAUTHORIZED, "G012", "이미 해당 리뷰에 평가를 하였습니다."),

    /**
     * 403
     */
    FORBIDDEN_ERROR(FORBIDDEN, "G001", "Forbidden Exception"),
    SELF_REVIEW_EVALUATION_LIKE_EXCEPTION(FORBIDDEN, "G002", "자신의 리뷰에는 평가, 좋아요를 할 수 없습니다."),

    /**
     * 404
     */
    NOT_FOUND_ERROR(NOT_FOUND, "G001", "Not Found Exception"),
    NULL_POINT_ERROR(NOT_FOUND, "G002", "Null Point Exception"),
    NOT_FOUND_EMAIL_EXCEPTION(NOT_FOUND, "G003", "존재하지 않는 계정입니다."),
    NOT_FOUND_USER_EXCEPTION(NOT_FOUND, "G004", "존재하지 않는 계정입니다."),
    NOT_FOUND_POST_EXCEPTION(NOT_FOUND, "G005", "존재하지 않는 게시글입니다."),
    NOT_FOUND_BOARD_CATEGORY_EXCEPTION(NOT_FOUND, "G006", "서비스 하지 않는 카테고리입니다."),
    NOT_FOUND_BOARD_REGION_EXCEPTION(NOT_FOUND, "G006", "서비스 하지 않는 지역입니다."),
    NOT_FOUND_COMMENT_EXCEPTION(NOT_FOUND, "G007", "존재하지 않는 댓글입니다."),
    NOT_FOUND_NOTIFICATION_EXCEPTION(NOT_FOUND, "G008", "존재하지 않는 알림입니다."),
    NOT_FOUND_REVIEW_EXCEPTION(NOT_FOUND, "G009", "존재하지 않는 리뷰입니다."),
    NOT_FOUND_REVIEW_CATEGORY_EXCEPTION(NOT_FOUND, "G010", "존재하지 않는 리뷰 카테고리입니다."),
    NOT_FOUND_PLACE_EXCEPTION(NOT_FOUND, "G010", "존재하지 않는 장소 입니다."),

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