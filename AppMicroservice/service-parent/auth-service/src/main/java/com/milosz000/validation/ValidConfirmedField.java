package com.milosz000.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidConfirmedFieldValidator.class)
@Documented
public @interface ValidConfirmedField {

    String message() default "Doesn't match the original";

    String originalField();

    String confirmationField();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        ValidConfirmedField[] value();
    }
}
