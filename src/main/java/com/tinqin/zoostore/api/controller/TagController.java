package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.tag.TagCreateRequest;
import com.tinqin.zoostore.api.request.tag.TagUpdateRequest;
import com.tinqin.zoostore.api.response.tag.TagArchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagCreateResponse;
import com.tinqin.zoostore.api.response.tag.TagUnarchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagUpdateResponse;
import com.tinqin.zoostore.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.exception.tag.OccupiedTagTitleException;
import com.tinqin.zoostore.exception.tag.TagAlreadyArchivedException;
import com.tinqin.zoostore.exception.tag.TagAlreadyUnarchivedException;
import com.tinqin.zoostore.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<TagCreateResponse> createTag(
            @Valid @RequestBody TagCreateRequest tagCreateRequest) {
        TagCreateResponse tagResponse = this.tagService.createTag(tagCreateRequest);

        return ResponseEntity.
                status(201).
                body(tagResponse);
    }

    @PatchMapping("/updateTag")
    public ResponseEntity<TagUpdateResponse> updateTag(
            @Valid @RequestBody TagUpdateRequest tagUpdateRequest) {
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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
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
