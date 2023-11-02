package ru.ssau.webcaffe.service;

import lombok.Data;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ValidationErrorResponse {

    private final Map<String, ?> violations;

    public ValidationErrorResponse() {
        violations = new HashMap<>(0);
    }

    public ValidationErrorResponse(Map<String, ?> violations) {
        this.violations = violations;
    }

    public static ValidationErrorResponse newFromBindings(
            BindingResult result
    ) {
        if(!result.hasErrors()) return new ValidationErrorResponse();
        if(result.getAllErrors().isEmpty()) return new ValidationErrorResponse();
        var violations = result.getAllErrors().stream().collect(Collectors.toMap(
                DefaultMessageSourceResolvable::getCode,
                err -> err.getDefaultMessage() == null
                        ? ""
                        : err.getDefaultMessage()
        ));
        return new ValidationErrorResponse(violations);
    }

    @Data
    public static class Violation implements Map.Entry<String, Object> {
        String fieldName;
        Object message;

        public Violation(String fieldName, Object message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        @Override
        public String getKey() {
            return fieldName;
        }

        @Override
        public Object getValue() {
            return message;
        }

        @Override
        public Object setValue(Object message) {
            this.message = message;
            return message;
        }

    }
}
