package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.TagCreateRequest;
import com.tinqin.zoostore.api.request.TagUpdateRequest;
import com.tinqin.zoostore.api.response.TagArchiveResponse;
import com.tinqin.zoostore.api.response.TagCreateResponse;
import com.tinqin.zoostore.api.response.TagUnarchiveResponse;
import com.tinqin.zoostore.api.response.TagUpdateResponse;
import com.tinqin.zoostore.exception.NoSuchTagException;
import com.tinqin.zoostore.exception.NullOrEmptyStringException;
import com.tinqin.zoostore.exception.OccupiedTagTitleException;
import com.tinqin.zoostore.exception.TagAlreadyArchivedException;
import com.tinqin.zoostore.exception.TagAlreadyUnarchivedException;
import com.tinqin.zoostore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/createTag")
    public ResponseEntity<TagCreateResponse> createTag(@RequestBody TagCreateRequest tagCreateRequest) {
        if (checkIfTitleIsNullOrEmpty(tagCreateRequest.getTitle())) {
            throw new NullOrEmptyStringException();
        }

        TagCreateResponse tagResponse = this.tagService.createTag(tagCreateRequest);

        return ResponseEntity.
                status(201).
                body(tagResponse);
    }

    @PatchMapping("/updateTag")
    public ResponseEntity<TagUpdateResponse> updateTag(@RequestBody TagUpdateRequest tagUpdateRequest) {
        String titleToUpdate = tagUpdateRequest.getOldTitle();
        String newTitle = tagUpdateRequest.getNewTitle();

        if (checkIfTitleIsNullOrEmpty(titleToUpdate) || checkIfTitleIsNullOrEmpty(newTitle)) {
            throw new NullOrEmptyStringException();
        }

        TagUpdateResponse updatedTag = this.tagService.updateTag(tagUpdateRequest);

        return ResponseEntity.ok(updatedTag);
    }

    @PatchMapping("/archiveTag/{title}")
    public ResponseEntity<TagArchiveResponse> archiveTag(@PathVariable String title) {
        TagArchiveResponse archivedTag = this.tagService.archiveTag(title);

        return ResponseEntity.ok(archivedTag);
    }

    @PatchMapping("/unarchiveTag/{title}")
    public ResponseEntity<TagUnarchiveResponse> unarchiveTag(@PathVariable String title) {
        TagUnarchiveResponse unarchivedTag = this.tagService.unarchiveTag(title);

        return ResponseEntity.ok(unarchivedTag);
    }

    private boolean checkIfTitleIsNullOrEmpty(String title) {
        return title == null || title.trim().equals("");
    }

    @ExceptionHandler(value = NullOrEmptyStringException.class)
    public ResponseEntity<String> handleTagTitleNullOrEmptyException(NullOrEmptyStringException ex) {
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

    @ExceptionHandler(value = TagAlreadyArchivedException.class)
    public ResponseEntity<String> handleTagAlreadyArchivedException(TagAlreadyArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = TagAlreadyUnarchivedException.class)
    public ResponseEntity<String> handleTagAlreadyUnarchivedException(TagAlreadyUnarchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
