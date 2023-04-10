package com.milosz000.exception;

import com.milosz000.exception.exceptions.ApiException;
import com.milosz000.exception.exceptions.ResetPasswordInvalidTokenException;
import com.milosz000.exception.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    //TODO: avoid boilerplate code (https://github.com/spring-projects/spring-framework/blob/main/spring-webmvc/src/main/java/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.java)

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {

        // 1.Create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );
        // 2.Return response entity
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<Object> handleResetPasswordInvalidTokenException(ApiRequestException e) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ResetPasswordInvalidTokenException exception = new ResetPasswordInvalidTokenException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(exception, badRequest);
    }

    @ExceptionHandler(value = {ExpiredTokenException.class})
    public ResponseEntity<Object> handleTokenExpiredException(ApiRequestException e) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        TokenExpiredException exception = new TokenExpiredException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now()
        );

        return new ResponseEntity<>(exception, badRequest);
    }
}
