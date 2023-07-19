package com.tinqin.zoostore.api.operations.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.regex.Pattern;

public class UuidValidator implements ConstraintValidator<UuidValidation, Set<String>> {

    @Override
    public void initialize(UuidValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set<String> ids, ConstraintValidatorContext context) {
        final Pattern pattern = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

        for (String id : ids) {
            if (id == null || id.trim().isEmpty() || !pattern.matcher(id).matches()) {
                return false;
            }
        }

        return true;
    }
}
