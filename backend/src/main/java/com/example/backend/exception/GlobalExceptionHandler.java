package com.example.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<String>> handleCustomException(CustomException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        String path = request.getRequestURI();
        String method = request.getMethod();
        String query = request.getQueryString();
        String message = ex.getMessage();

        if (query != null) {
            log.warn("Business exception [{} {}?{}]: code={}, message={}", method, path, query, errorCode.getCode(), message);
        } else {
            log.warn("Business exception [{} {}]: code={}, message={}", method, path, errorCode.getCode(), message);
        }

        return ResponseEntity
                .status(errorCode.getHttpStatus().value())
                .body(BaseResponse.error(errorCode, path));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseResponse<String>> handleAuthenticationException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String query = request.getQueryString();

        ErrorCode code = ErrorCode.LOGIN_FAILED;

        if (query != null) {
            log.error("Authentication exception [{} {}?{}]: code={}", method, path, query, code.getCode());
        } else {
            log.error("Authentication exception [{}]: code={}", method, code.getCode());
        }

        return ResponseEntity
                .status(code.getHttpStatus().value())
                .body(BaseResponse.error(code, path));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();
        String query = request.getQueryString();

        // 예기치 않은 예외: 스택 트레이스를 남겨야 원인 파악 가능
        if (query != null) {
            log.error("Unhandled exception [{} {}?{}]: {}", method, path, query, ex.getMessage(), ex);
        } else {
            log.error("Unhandled exception [{} {}]: {}", method, path, ex.getMessage(), ex);
        }

        ErrorCode code = ErrorCode.UNKNOWN_ERROR;

        return ResponseEntity
                .status(code.getHttpStatus().value())
                .body(BaseResponse.error(code, path));
    }

}
