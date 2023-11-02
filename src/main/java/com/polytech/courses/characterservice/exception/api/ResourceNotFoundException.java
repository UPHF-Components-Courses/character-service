package com.polytech.courses.characterservice.exception.api;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(Class<?> resourceType) {
        super(HttpStatus.NOT_FOUND, "No %s has been found".formatted(resourceType.getSimpleName()));
    }

    public ResourceNotFoundException(Class<?> resourceType, String property, Object value) {
        super(
            HttpStatus.NOT_FOUND,
            "No %s has been found by %s with value %s".formatted(
                resourceType.getSimpleName(),
                property,
                value.toString()
            )
        );
    }
}
