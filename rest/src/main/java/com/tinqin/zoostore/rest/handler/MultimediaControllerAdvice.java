package com.tinqin.zoostore.rest.handler;

import com.tinqin.zoostore.core.exception.multimedia.MultimediaDeletionException;
import com.tinqin.zoostore.core.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.core.exception.multimedia.UnsupportedFileTypeException;
import com.tinqin.zoostore.rest.controller.MultimediaController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {MultimediaController.class})
public class MultimediaControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
    }

    @ExceptionHandler(value = UnsupportedFileTypeException.class)
    public ResponseEntity<String> handleUnsupportedFileTypeException(UnsupportedFileTypeException ex) {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchMultimediaException.class)
    public ResponseEntity<String> handleNoSuchMultimediaException(NoSuchMultimediaException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(value = MultimediaDeletionException.class)
    public ResponseEntity<String> handleMultimediaDeletionException(MultimediaDeletionException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
