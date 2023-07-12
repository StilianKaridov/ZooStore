package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.multimedia.MultimediaDeleteRequest;
import com.tinqin.zoostore.api.request.multimedia.MultimediaRetrieveRequest;
import com.tinqin.zoostore.api.request.multimedia.MultimediaUploadRequest;
import com.tinqin.zoostore.api.response.multimedia.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaUploadResponse;
import com.tinqin.zoostore.exception.multimedia.MultimediaDeletionException;
import com.tinqin.zoostore.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.exception.multimedia.UnsupportedFileTypeException;
import com.tinqin.zoostore.service.MultimediaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class MultimediaController {

    private final MultimediaService multimediaService;

    @Autowired
    public MultimediaController(MultimediaService multimediaService) {
        this.multimediaService = multimediaService;
    }

    @GetMapping("/retrieveMultimedia")
    public ResponseEntity<MultimediaRetrieveResponse> retrieve(
            @Valid @RequestBody MultimediaRetrieveRequest multimediaRetrieveRequest
    ) {
        MultimediaRetrieveResponse multimedia = this.multimediaService.retrieveMultimedia(multimediaRetrieveRequest.getPublicId());

        return ResponseEntity.ok(multimedia);
    }

    @PostMapping("/uploadMultimedia")
    public ResponseEntity<MultimediaUploadResponse> upload(
            @Valid MultimediaUploadRequest fileUpload
    ) throws IOException {
        MultimediaUploadResponse response = this.multimediaService.uploadMultimedia(fileUpload);

        return ResponseEntity.
                status(201).
                body(response);
    }

    @Transactional
    @DeleteMapping("/deleteMultimedia")
    public ResponseEntity<MultimediaDeleteResponse> delete(
            @Valid @RequestBody MultimediaDeleteRequest multimediaDeleteRequest
    ) {
        MultimediaDeleteResponse deletedMultimedia = this.multimediaService.deleteMultimedia(multimediaDeleteRequest.getPublicId());

        return ResponseEntity.ok(deletedMultimedia);
    }

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
