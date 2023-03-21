package com.milosz000.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.annotation.Annotation;

public class ValidConfirmedFieldValidator implements ConstraintValidator<ValidConfirmedField, Object> {

    private String originalField;
    private String confirmationField;
    private String message;

    @Override
    public void initialize(ValidConfirmedField constraintAnnotation) {

        this.originalField = constraintAnnotation.originalField();
        this.confirmationField = constraintAnnotation.confirmationField();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(originalField);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(confirmationField);

        boolean isValid = fieldValue != null && fieldValue.equals(confirmationField);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmationField)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
