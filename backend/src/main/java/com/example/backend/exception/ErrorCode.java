package com.example.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ErrorCode의 양식
 * 1. 이름: 도메인_상황
 * 2. 내용: http 상태코드, 분류코드(이름_상태코드), 사용자 친화적 메시지
 * 각 도메인 별로 ErrorCode를 분리해주세요.
 */
@Getter
public enum ErrorCode {

    LOGIN_FAILED(HttpStatus.BAD_REQUEST, "LOGIN_FAILED_400", "로그인 실패"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR_500", "처리되지 않은 서버 오류"),

    USER_EMAIL_DUPLICATED(HttpStatus.BAD_REQUEST, "USER_EMAIL_DUPLICATED_400", "이미 사용 중인 이메일입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
