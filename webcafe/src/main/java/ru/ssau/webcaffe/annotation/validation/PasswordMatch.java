package ru.ssau.webcaffe.annotation.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {
    String message() default "{PasswordMatch.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
