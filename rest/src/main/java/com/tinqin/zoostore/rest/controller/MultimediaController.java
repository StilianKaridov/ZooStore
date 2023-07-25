package com.tinqin.zoostore.rest.controller;

import com.tinqin.zoostore.api.operations.multimedia.delete.MultimediaDeleteOperation;
import com.tinqin.zoostore.api.operations.multimedia.delete.MultimediaDeleteRequest;
import com.tinqin.zoostore.api.operations.multimedia.delete.MultimediaDeleteResponse;
import com.tinqin.zoostore.api.operations.multimedia.retrieve.MultimediaRetrieveOperation;
import com.tinqin.zoostore.api.operations.multimedia.retrieve.MultimediaRetrieveRequest;
import com.tinqin.zoostore.api.operations.multimedia.retrieve.MultimediaRetrieveResponse;
import com.tinqin.zoostore.api.operations.multimedia.upload.MultimediaUploadOperation;
import com.tinqin.zoostore.api.operations.multimedia.upload.MultimediaUploadRequest;
import com.tinqin.zoostore.api.operations.multimedia.upload.MultimediaUploadResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zoostore/multimedia")
public class MultimediaController {

    private final MultimediaRetrieveOperation multimediaRetrieveOperation;
    private final MultimediaUploadOperation multimediaUploadOperation;
    private final MultimediaDeleteOperation multimediaDeleteOperation;

    @Autowired
    public MultimediaController(
            MultimediaRetrieveOperation multimediaRetrieveOperation,
            MultimediaUploadOperation multimediaUploadOperation,
            MultimediaDeleteOperation multimediaDeleteOperation
    ) {
        this.multimediaRetrieveOperation = multimediaRetrieveOperation;
        this.multimediaUploadOperation = multimediaUploadOperation;
        this.multimediaDeleteOperation = multimediaDeleteOperation;
    }


    @GetMapping("/{publicId}")
    public ResponseEntity<MultimediaRetrieveResponse> retrieve(
            @PathVariable String publicId
    ) {
        MultimediaRetrieveRequest multimediaRetrieveRequest = MultimediaRetrieveRequest
                .builder()
                .publicId(publicId)
                .build();

        MultimediaRetrieveResponse multimedia = this.multimediaRetrieveOperation.process(multimediaRetrieveRequest);

        return ResponseEntity.ok(multimedia);
    }

    @PostMapping
    public ResponseEntity<MultimediaUploadResponse> upload(
            @Valid MultimediaUploadRequest fileUpload
    ) {
        MultimediaUploadResponse response = this.multimediaUploadOperation.process(fileUpload);

        return ResponseEntity.
                status(201).
                body(response);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<MultimediaDeleteResponse> delete(
            @Valid @RequestBody MultimediaDeleteRequest multimediaDeleteRequest
    ) {
        MultimediaDeleteResponse deletedMultimedia = this.multimediaDeleteOperation.process(multimediaDeleteRequest);

        return ResponseEntity.ok(deletedMultimedia);
    }
}
