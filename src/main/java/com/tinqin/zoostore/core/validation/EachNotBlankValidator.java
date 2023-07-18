package com.tinqin.zoostore.core.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class EachNotBlankValidator implements ConstraintValidator<EachNotBlank, Set<String>> {

    @Override
    public void initialize(EachNotBlank constraintAnnotation) {
    }

    @Override
    public boolean isValid(Set<String> value, ConstraintValidatorContext context) {
        for (String element : value) {
            if (element == null || element.trim().isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
