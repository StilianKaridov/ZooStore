package com.tinqin.zoostore.rest.controller;

import com.tinqin.zoostore.api.operations.tag.archive.TagArchiveOperation;
import com.tinqin.zoostore.api.operations.tag.archive.TagArchiveRequest;
import com.tinqin.zoostore.api.operations.tag.archive.TagArchiveResponse;
import com.tinqin.zoostore.api.operations.tag.create.TagCreateOperation;
import com.tinqin.zoostore.api.operations.tag.create.TagCreateRequest;
import com.tinqin.zoostore.api.operations.tag.create.TagCreateResponse;
import com.tinqin.zoostore.api.operations.tag.unarchive.TagUnarchiveOperation;
import com.tinqin.zoostore.api.operations.tag.unarchive.TagUnarchiveRequest;
import com.tinqin.zoostore.api.operations.tag.unarchive.TagUnarchiveResponse;
import com.tinqin.zoostore.api.operations.tag.update.TagUpdateOperation;
import com.tinqin.zoostore.api.operations.tag.update.TagUpdateRequest;
import com.tinqin.zoostore.api.operations.tag.update.TagUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zoostore/tags")
public class TagController {

    private final TagCreateOperation tagCreateOperation;
    private final TagUpdateOperation tagUpdateOperation;
    private final TagArchiveOperation tagArchiveOperation;
    private final TagUnarchiveOperation tagUnarchiveOperation;

    @Autowired
    public TagController(
            TagCreateOperation tagCreateOperation,
            TagUpdateOperation tagUpdateOperation,
            TagArchiveOperation tagArchiveOperation,
            TagUnarchiveOperation tagUnarchiveOperation
    ) {
        this.tagCreateOperation = tagCreateOperation;
        this.tagUpdateOperation = tagUpdateOperation;
        this.tagArchiveOperation = tagArchiveOperation;
        this.tagUnarchiveOperation = tagUnarchiveOperation;
    }

    @Operation(description = "Creates tag with title.",
            summary = "Creates tag.")
    @ApiResponse(responseCode = "201", description = "Successfully created tag.")
    @ApiResponse(responseCode = "400",
            description = "Tag exists.",
            content = {@Content(examples = @ExampleObject(value = "This title is occupied. Please, choose a new title!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Title must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The title must not be empty!"), mediaType = "text/html")})
    @PostMapping
    public ResponseEntity<TagCreateResponse> createTag(
            @Valid @RequestBody TagCreateRequest tagCreateRequest
    ) {
        TagCreateResponse tagResponse = this.tagCreateOperation.process(tagCreateRequest);

        return ResponseEntity.
                status(201).
                body(tagResponse);
    }

    @Operation(description = "Updates tag title.",
            summary = "Updates tag.")
    @ApiResponse(responseCode = "200", description = "Successfully updated tag title.")
    @ApiResponse(responseCode = "400",
            description = "Tag exists.",
            content = {@Content(examples = @ExampleObject(value = "This title is occupied. Please, choose a new title!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Tag does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This tag does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Old title and new title must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The title must not be empty!"), mediaType = "text/html")})
    @PatchMapping("/update")
    public ResponseEntity<TagUpdateResponse> updateTag(
            @Valid @RequestBody TagUpdateRequest tagUpdateRequest
    ) {
        TagUpdateResponse updatedTag = this.tagUpdateOperation.process(tagUpdateRequest);

        return ResponseEntity.ok(updatedTag);
    }

    @Operation(description = "Archives tag.",
            summary = "Archives tag.")
    @ApiResponse(responseCode = "200", description = "Successfully archived tag.")
    @ApiResponse(responseCode = "400",
            description = "Tag already archived.",
            content = {@Content(examples = @ExampleObject(value = "Tag is already archived!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Tag does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This tag does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Title must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The title must not be empty!"), mediaType = "text/html")})
    @PatchMapping("/archive")
    public ResponseEntity<TagArchiveResponse> archiveTag(
            @Valid @RequestBody TagArchiveRequest tagArchiveRequest
    ) {
        TagArchiveResponse archivedTag = this.tagArchiveOperation.process(tagArchiveRequest);

        return ResponseEntity.ok(archivedTag);
    }

    @Operation(description = "Unarchive tag.",
            summary = "Unarchive tag.")
    @ApiResponse(responseCode = "200", description = "Successfully unarchived tag.")
    @ApiResponse(responseCode = "400",
            description = "Tag already unarchived.",
            content = {@Content(examples = @ExampleObject(value = "Tag is already unarchived!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Tag does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This tag does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Title must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The title must not be empty!"), mediaType = "text/html")})
    @PatchMapping("/unarchive")
    public ResponseEntity<TagUnarchiveResponse> unarchiveTag(
            @Valid @RequestBody TagUnarchiveRequest tagUnarchiveRequest
    ) {
        TagUnarchiveResponse unarchivedTag = this.tagUnarchiveOperation.process(tagUnarchiveRequest);

        return ResponseEntity.ok(unarchivedTag);
    }
}
