package com.polytech.courses.characterservice.exception.api;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiExceptionResponse {

    private String message;

    private String path;

    private Instant timestamp;

    private String type;

    private int code;
}
