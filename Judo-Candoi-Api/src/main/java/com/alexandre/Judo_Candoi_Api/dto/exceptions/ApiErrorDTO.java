package com.alexandre.Judo_Candoi_Api.dto.exceptions;

import java.time.LocalDateTime;

public record ApiErrorDTO(
        int status,
        String error,
        String message,
        LocalDateTime timestamp,
        Object details
) {
    public ApiErrorDTO(int status, String error, String message, Object details) {
        this(status, error, message, LocalDateTime.now(), details);
    }

    public ApiErrorDTO(int status, String error, String message) {
        this(status, error, message, LocalDateTime.now(), null);
    }
}
