package com.polytech.courses.characterservice.exception.api;

import org.springframework.http.HttpStatus;

public class InvalidDataException extends ApiException {

    public InvalidDataException(String reason) {
        super(HttpStatus.BAD_REQUEST, "Invalid data provided: " + reason);
    }
}
