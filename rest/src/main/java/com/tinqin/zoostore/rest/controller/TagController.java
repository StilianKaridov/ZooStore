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


    @PostMapping
    public ResponseEntity<TagCreateResponse> createTag(
            @Valid @RequestBody TagCreateRequest tagCreateRequest
    ) {
        TagCreateResponse tagResponse = this.tagCreateOperation.process(tagCreateRequest);

        return ResponseEntity.
                status(201).
                body(tagResponse);
    }

    @PatchMapping("/update")
    public ResponseEntity<TagUpdateResponse> updateTag(
            @Valid @RequestBody TagUpdateRequest tagUpdateRequest
    ) {
        TagUpdateResponse updatedTag = this.tagUpdateOperation.process(tagUpdateRequest);

        return ResponseEntity.ok(updatedTag);
    }

    @PatchMapping("/archive")
    public ResponseEntity<TagArchiveResponse> archiveTag(
            @Valid @RequestBody TagArchiveRequest tagArchiveRequest
    ) {
        TagArchiveResponse archivedTag = this.tagArchiveOperation.process(tagArchiveRequest);

        return ResponseEntity.ok(archivedTag);
    }

    @PatchMapping("/unarchive")
    public ResponseEntity<TagUnarchiveResponse> unarchiveTag(
            @Valid @RequestBody TagUnarchiveRequest tagUnarchiveRequest
    ) {
        TagUnarchiveResponse unarchivedTag = this.tagUnarchiveOperation.process(tagUnarchiveRequest);

        return ResponseEntity.ok(unarchivedTag);
    }
}
