package com.polytech.courses.characterservice.exception.api;

import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    protected ResponseEntity<Object> handleApiException(ApiException exception, ServletWebRequest request) {
        log.debug(exception.getMessage());

        final ApiExceptionResponse response = ApiExceptionResponse.builder()
            .message(exception.getMessage())
            .type(exception.getClass().getSimpleName())
            .timestamp(Instant.now())
            .path(request.getRequest().getServletPath())
            .code(exception.getHttpStatus().value())
            .build();

        return ResponseEntity.status(exception.getHttpStatus())
            .body(response);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleOther(Exception exception, ServletWebRequest request) {
        log.error(exception.getMessage(), exception);

        final ApiExceptionResponse response = ApiExceptionResponse.builder()
            .message(exception.getMessage())
            .type(exception.getClass().getSimpleName())
            .timestamp(Instant.now())
            .path(request.getRequest().getServletPath())
            .code(500) // 500 for every not handled exceptions
            .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }
}
