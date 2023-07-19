package com.tinqin.zoostore.rest.handler;

import com.tinqin.zoostore.core.exception.item.ItemAlreadyExistingException;
import com.tinqin.zoostore.core.exception.item.ItemAlreadyUnarchivedException;
import com.tinqin.zoostore.core.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.core.exception.tag.TagDoesntBelongToItemException;
import com.tinqin.zoostore.rest.controller.ItemController;
import com.tinqin.zoostore.core.exception.item.ItemAlreadyArchivedException;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.core.exception.multimedia.MultimediaDoesntBelongItemException;
import com.tinqin.zoostore.core.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.core.exception.vendor.NoSuchVendorException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {ItemController.class})
public class ItemControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
    }

    @ExceptionHandler(value = NoSuchItemException.class)
    public ResponseEntity<String> handleNoSuchItemException(NoSuchItemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchVendorException.class)
    public ResponseEntity<String> handleNoSuchVendorException(NoSuchVendorException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchTagException.class)
    public ResponseEntity<String> handleNoSuchTagException(NoSuchTagException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = MultimediaDoesntBelongItemException.class)
    public ResponseEntity<String> handleNoSuchMultimediaInItemException(MultimediaDoesntBelongItemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = TagDoesntBelongToItemException.class)
    public ResponseEntity<String> handleNoSuchTagInItemException(TagDoesntBelongToItemException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchMultimediaException.class)
    public ResponseEntity<String> handleNoSuchMultimediaException(NoSuchMultimediaException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = ItemAlreadyExistingException.class)
    public ResponseEntity<String> handleItemAlreadyExistingException(ItemAlreadyExistingException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = ItemAlreadyArchivedException.class)
    public ResponseEntity<String> handleItemAlreadyArchivedException(ItemAlreadyArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = ItemAlreadyUnarchivedException.class)
    public ResponseEntity<String> handleItemAlreadyUnarchivedException(ItemAlreadyUnarchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
