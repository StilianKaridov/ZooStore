package com.tinqin.zoostore.exception.exceptionHandler;

import com.tinqin.zoostore.api.controller.TagController;
import com.tinqin.zoostore.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.exception.tag.OccupiedTagTitleException;
import com.tinqin.zoostore.exception.tag.TagAlreadyArchivedException;
import com.tinqin.zoostore.exception.tag.TagAlreadyUnarchivedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {TagController.class})
public class TagControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
    }

    @ExceptionHandler(value = OccupiedTagTitleException.class)
    public ResponseEntity<String> handleOccupiedTagTitleException(OccupiedTagTitleException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchTagException.class)
    public ResponseEntity<String> handleNoSuchTagException(NoSuchTagException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(value = TagAlreadyArchivedException.class)
    public ResponseEntity<String> handleTagAlreadyArchivedException(TagAlreadyArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = TagAlreadyUnarchivedException.class)
    public ResponseEntity<String> handleTagAlreadyUnarchivedException(TagAlreadyUnarchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
