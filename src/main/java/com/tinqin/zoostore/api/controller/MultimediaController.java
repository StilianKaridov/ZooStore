package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.MultimediaUploadRequest;
import com.tinqin.zoostore.api.response.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.response.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.response.MultimediaUploadResponse;
import com.tinqin.zoostore.exception.MissingFileException;
import com.tinqin.zoostore.exception.MultimediaDeletionException;
import com.tinqin.zoostore.exception.NoSuchMultimediaException;
import com.tinqin.zoostore.exception.NullOrEmptyStringException;
import com.tinqin.zoostore.exception.UnsupportedFileTypeException;
import com.tinqin.zoostore.service.MultimediaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<MultimediaRetrieveResponse> retrieve(@RequestParam(name = "public_id") String publicId) {
        if (publicId == null || publicId.trim().equals("")) {
            throw new NullOrEmptyStringException();
        }

        MultimediaRetrieveResponse multimedia = this.multimediaService.retrieveMultimedia(publicId);

        return ResponseEntity.ok(multimedia);
    }

    @PostMapping("/uploadMultimedia")
    public ResponseEntity<MultimediaUploadResponse> upload(MultimediaUploadRequest fileUpload) throws IOException {
        if (fileUpload.getFile().isEmpty()) {
            throw new MissingFileException();
        }

        MultimediaUploadResponse response = this.multimediaService.uploadMultimedia(fileUpload);

        return ResponseEntity.
                status(201).
                body(response);
    }

    @Transactional
    @DeleteMapping("/deleteMultimedia")
    public ResponseEntity<MultimediaDeleteResponse> delete(@RequestParam(name = "public_id") String publicId) {
        if (publicId == null || publicId.trim().equals("")) {
            throw new NullOrEmptyStringException();
        }

        MultimediaDeleteResponse deletedMultimedia = this.multimediaService.deleteMultimedia(publicId);

        return ResponseEntity.ok(deletedMultimedia);
    }

    @ExceptionHandler(value = MissingFileException.class)
    public ResponseEntity<String> handleMissingFileException(MissingFileException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(value = NullOrEmptyStringException.class)
    public ResponseEntity<String> handleNullOrEmptyPublicIdException(NullOrEmptyStringException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
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
