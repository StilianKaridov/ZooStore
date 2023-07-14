package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.tag.TagCreateRequest;
import com.tinqin.zoostore.api.request.tag.TagUpdateRequest;
import com.tinqin.zoostore.api.response.tag.TagArchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagCreateResponse;
import com.tinqin.zoostore.api.response.tag.TagUnarchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagUpdateResponse;
import com.tinqin.zoostore.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
}
