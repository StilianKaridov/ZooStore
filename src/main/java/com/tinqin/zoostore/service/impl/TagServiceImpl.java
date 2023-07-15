package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.tag.TagCreateRequest;
import com.tinqin.zoostore.api.request.tag.TagUpdateRequest;
import com.tinqin.zoostore.api.response.tag.TagArchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagCreateResponse;
import com.tinqin.zoostore.api.response.tag.TagUnarchiveResponse;
import com.tinqin.zoostore.api.response.tag.TagUpdateResponse;
import com.tinqin.zoostore.data.entity.Tag;
import com.tinqin.zoostore.data.repository.TagRepository;
import com.tinqin.zoostore.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.exception.tag.OccupiedTagTitleException;
import com.tinqin.zoostore.exception.tag.TagAlreadyArchivedException;
import com.tinqin.zoostore.exception.tag.TagAlreadyUnarchivedException;
import com.tinqin.zoostore.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagCreateResponse createTag(TagCreateRequest tagCreateRequest) {
        String title = tagCreateRequest.getTitle();

        if (checkIfTitleExists(title)) {
            throw new OccupiedTagTitleException();
        }

        Tag tagEntity = Tag
                .builder()
                .title(title)
                .isArchived(Boolean.FALSE)
                .build();

        this.tagRepository.save(tagEntity);

        return this.modelMapper.map(tagEntity, TagCreateResponse.class);
    }

    @Override
    public TagUpdateResponse updateTag(TagUpdateRequest tagUpdateRequest) {
        String oldTitle = tagUpdateRequest.getOldTitle();
        String newTitle = tagUpdateRequest.getNewTitle();

        Tag tag = this.tagRepository
                .findTagByTitle(oldTitle)
                .orElseThrow(NoSuchTagException::new);

        if (checkIfTitleExists(newTitle)) {
            throw new OccupiedTagTitleException();
        }

        Tag updated = Tag
                .builder()
                .id(tag.getId())
                .title(newTitle)
                .isArchived(tag.getIsArchived())
                .items(tag.getItems())
                .build();

        this.tagRepository.save(updated);

        return this.modelMapper.map(updated, TagUpdateResponse.class);
    }

    @Override
    public TagArchiveResponse archiveTag(String title) {
        Tag tag = this.tagRepository.
                findTagByTitle(title).
                orElseThrow(NoSuchTagException::new);

        if (tag.getIsArchived()) {
            throw new TagAlreadyArchivedException();
        }

        Tag archivedTag = Tag
                .builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .isArchived(Boolean.TRUE)
                .items(tag.getItems())
                .build();

        this.tagRepository.save(archivedTag);

        return this.modelMapper.map(archivedTag, TagArchiveResponse.class);
    }

    @Override
    public TagUnarchiveResponse unarchiveTag(String title) {
        Tag tag = this.tagRepository.
                findTagByTitle(title).
                orElseThrow(NoSuchTagException::new);

        if (!tag.getIsArchived()) {
            throw new TagAlreadyUnarchivedException();
        }

        Tag unarchivedTag = Tag
                .builder()
                .id(tag.getId())
                .title(tag.getTitle())
                .isArchived(Boolean.FALSE)
                .items(tag.getItems())
                .build();

        this.tagRepository.save(unarchivedTag);

        return this.modelMapper.map(unarchivedTag, TagUnarchiveResponse.class);
    }

    private boolean checkIfTitleExists(String title) {
        return this.tagRepository.findTagByTitle(title).isPresent();
    }
}
