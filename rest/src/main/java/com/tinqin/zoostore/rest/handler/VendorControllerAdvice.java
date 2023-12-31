package com.tinqin.zoostore.rest.handler;

import com.tinqin.zoostore.core.exception.vendor.InvalidPhoneNumberFormatException;
import com.tinqin.zoostore.core.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.core.exception.vendor.VendorAlreadyExistingException;
import com.tinqin.zoostore.core.exception.vendor.VendorAlreadyUnarchivedException;
import com.tinqin.zoostore.core.exception.vendor.VendorArchivedException;
import com.tinqin.zoostore.rest.controller.VendorController;
import com.tinqin.zoostore.core.exception.vendor.VendorAlreadyArchivedException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {VendorController.class})
public class VendorControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
    }

    @ExceptionHandler(value = InvalidPhoneNumberFormatException.class)
    public ResponseEntity<String> handleInvalidPhoneNumberFormatException(InvalidPhoneNumberFormatException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyExistingException.class)
    public ResponseEntity<String> handleExistingVendorException(VendorAlreadyExistingException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchVendorException.class)
    public ResponseEntity<String> handleNotExistingVendorException(NoSuchVendorException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyArchivedException.class)
    public ResponseEntity<String> handleVendorAlreadyArchivedException(VendorAlreadyArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyUnarchivedException.class)
    public ResponseEntity<String> handleVendorAlreadyUnarchivedException(VendorAlreadyUnarchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorArchivedException.class)
    public ResponseEntity<String> handleOnVendorDeleteIsArchivedException(VendorArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
