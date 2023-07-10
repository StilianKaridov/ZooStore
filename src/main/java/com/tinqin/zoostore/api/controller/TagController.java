package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.TagCreateRequest;
import com.tinqin.zoostore.api.request.TagUpdateRequest;
import com.tinqin.zoostore.api.response.TagCreateResponse;
import com.tinqin.zoostore.api.response.TagUpdateResponse;
import com.tinqin.zoostore.exception.NoSuchTagException;
import com.tinqin.zoostore.exception.NullOrEmptyTitleException;
import com.tinqin.zoostore.exception.OccupiedTagTitleException;
import com.tinqin.zoostore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    private static final String SUCCESSFULLY_CREATED_TAG_RESP_MESSAGE = "Successfully created Tag with title %s";
    private static final String SUCCESSFULLY_UPDATED_TAG_RESP_MESSAGE = "Successfully updated Tag title from %s to %s";

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/createTag")
    public ResponseEntity<String> createTag(@RequestBody TagCreateRequest tagCreateRequest) {
        if (checkIfTitleIsNullOrEmpty(tagCreateRequest.getTitle())) {
            throw new NullOrEmptyTitleException();
        }

        TagCreateResponse tag = this.tagService.createTag(tagCreateRequest);

        return ResponseEntity.
                status(201).
                body(String.format(
                                SUCCESSFULLY_CREATED_TAG_RESP_MESSAGE, tag.getTitle()
                        )
                );
    }

    @PatchMapping("/updateTag")
    public ResponseEntity<String> updateTag(@RequestBody TagUpdateRequest tagUpdateRequest) {
        String titleToUpdate = tagUpdateRequest.getOldTitle();
        String newTitle = tagUpdateRequest.getNewTitle();

        if (checkIfTitleIsNullOrEmpty(titleToUpdate) || checkIfTitleIsNullOrEmpty(newTitle)) {
            throw new NullOrEmptyTitleException();
        }

        TagUpdateResponse updatedTag = this.tagService.updateTag(tagUpdateRequest);

        return ResponseEntity.ok(
                String.format(
                        SUCCESSFULLY_UPDATED_TAG_RESP_MESSAGE, titleToUpdate, updatedTag.getTitle()
                )
        );
    }

    private boolean checkIfTitleIsNullOrEmpty(String title) {
        return title == null || title.trim().equals("");
    }

    @ExceptionHandler(value = NullOrEmptyTitleException.class)
    public ResponseEntity<String> handleTagTitleNullOrEmptyException(NullOrEmptyTitleException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = OccupiedTagTitleException.class)
    public ResponseEntity<String> handleOccupiedTagTitleException(OccupiedTagTitleException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchTagException.class)
    public ResponseEntity<String> handleNoSuchTagException(NoSuchTagException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}