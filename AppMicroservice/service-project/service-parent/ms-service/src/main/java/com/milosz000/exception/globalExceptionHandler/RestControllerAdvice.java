package com.milosz000.exception.globalExceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

// RestControllerAdvice annotation
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice {
}
