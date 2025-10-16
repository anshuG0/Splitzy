package org.splitzy.common.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends BusinessException {
    public ValidationException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", cause);
    }
    public ValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "VALIDATION_ERROR");
    }
}
