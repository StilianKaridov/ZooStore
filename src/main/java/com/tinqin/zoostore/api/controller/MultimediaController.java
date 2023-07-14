package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.multimedia.MultimediaDeleteRequest;
import com.tinqin.zoostore.api.request.multimedia.MultimediaRetrieveRequest;
import com.tinqin.zoostore.api.request.multimedia.MultimediaUploadRequest;
import com.tinqin.zoostore.api.response.multimedia.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.response.multimedia.MultimediaUploadResponse;
import com.tinqin.zoostore.service.MultimediaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
}
