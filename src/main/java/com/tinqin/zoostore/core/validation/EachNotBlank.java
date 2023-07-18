package com.tinqin.zoostore.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EachNotBlankValidator.class)
@Documented
public @interface EachNotBlank {

    String message() default "Each element of the collection must not be blank!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
