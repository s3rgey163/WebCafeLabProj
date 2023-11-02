package ru.ssau.webcaffe.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.ssau.webcaffe.payload.request.SignupRequest;

import java.util.Objects;

class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, SignupRequest> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(SignupRequest sign, ConstraintValidatorContext context) {
        return Objects.equals(sign.getPassword(), sign.getConfirmPassword());
    }
}
