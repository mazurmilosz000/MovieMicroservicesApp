package com.milosz000.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.List;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private String message;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        PasswordValidator validator = new PasswordValidator(List.of(

                // length between 8 and 16 characters
                new LengthRule(8,16),

                // at least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),

                // at least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),

                // at least one special character
                new CharacterRule(EnglishCharacterData.Special,1),

                // no whitespace
                new WhitespaceRule(),

                // rejects passwords that contain a sequence of >= 5 characters, alphabetical (e.g. abcdef)
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),

                // rejects passwords that contain a sequence of >= 5 characters numerical (e.g. 12345)
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false)
        ));

        // check if password is not blank and if its valid
        boolean isValid = (password != null && password.trim().length() != 0) &&
                        validator.validate(new PasswordData(password)).isValid();


        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return isValid;
    }
}
