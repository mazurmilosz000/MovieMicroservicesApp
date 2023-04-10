package com.milosz000.exception.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record TokenExpiredException(String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
}
