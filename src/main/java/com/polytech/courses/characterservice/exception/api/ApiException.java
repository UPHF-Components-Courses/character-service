package com.polytech.courses.characterservice.exception.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus, String message, Throwable e) {
        super(message, e);
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, null);
    }
}
