package com.tinqin.zoostore.api.operations.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileNotEmptyValidator  implements ConstraintValidator<FileNotEmpty, MultipartFile> {

    @Override
    public void initialize(FileNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file != null && !file.isEmpty();
    }
}
