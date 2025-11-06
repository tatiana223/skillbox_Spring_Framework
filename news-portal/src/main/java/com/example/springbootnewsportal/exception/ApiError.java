package com.example.springbootnewsportal.exception;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ApiError {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(int status, String message, Map<String, String> errors) {
        this(status, message);
        this.errors = errors;
    }
}