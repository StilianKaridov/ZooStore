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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(description = "Retrieves multimedia from cloudinary.",
            summary = "Retrieves multimedia.")
    @ApiResponse(responseCode = "200", description = "Multimedia found.")
    @ApiResponse(responseCode = "400",
            description = "Multimedia does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No multimedia with that public ID!"), mediaType = "text/html")})
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

    @Operation(description = "Uploads multimedia to cloudinary.",
            summary = "Uploads multimedia.")
    @ApiResponse(responseCode = "201", description = "Multimedia uploaded.")
    @ApiResponse(responseCode = "400",
            description = "Unsupported file type.",
            content = {@Content(examples = @ExampleObject(value = "Unsupported file type! You can upload only images and videos!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Not selected file.",
            content = {@Content(examples = @ExampleObject(value = "Please, select a file!"), mediaType = "text/html")})
    @PostMapping
    public ResponseEntity<MultimediaUploadResponse> upload(
            @Valid MultimediaUploadRequest fileUpload
    ) {
        MultimediaUploadResponse response = this.multimediaUploadOperation.process(fileUpload);

        return ResponseEntity.
                status(201).
                body(response);
    }

    @Operation(description = "Deletes multimedia from cloudinary.",
            summary = "Deletes multimedia.")
    @ApiResponse(responseCode = "200", description = "Multimedia deleted.")
    @ApiResponse(responseCode = "400",
            description = "Multimedia does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No multimedia with that public ID!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Error during deletion.",
            content = {@Content(examples = @ExampleObject(value = "Error occurred during image deletion."), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Public id must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The public id must not be empty!"), mediaType = "text/html")})
    @Transactional
    @DeleteMapping
    public ResponseEntity<MultimediaDeleteResponse> delete(
            @Valid @RequestBody MultimediaDeleteRequest multimediaDeleteRequest
    ) {
        MultimediaDeleteResponse deletedMultimedia = this.multimediaDeleteOperation.process(multimediaDeleteRequest);

        return ResponseEntity.ok(deletedMultimedia);
    }
}
