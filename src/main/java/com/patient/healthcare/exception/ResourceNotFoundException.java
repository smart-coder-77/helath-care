package com.patient.healthcare.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final ApiError apiError;
    public ResourceNotFoundException(String message) {
        super(message);
        this.apiError = new ApiError(HttpStatus.NOT_FOUND.value(), getMessage());
    }

    public ApiError getApiError() {
        return apiError;
    }
}
