package com.milosz000.exception.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ResetPasswordInvalidTokenException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
}
