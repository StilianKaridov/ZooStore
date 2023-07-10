package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.TagCreateRequest;
import com.tinqin.zoostore.api.request.TagUpdateRequest;
import com.tinqin.zoostore.api.response.TagCreateResponse;
import com.tinqin.zoostore.api.response.TagUpdateResponse;
import com.tinqin.zoostore.data.entity.Tag;
import com.tinqin.zoostore.data.repository.TagRepository;
import com.tinqin.zoostore.exception.NoSuchTagException;
import com.tinqin.zoostore.exception.OccupiedTagTitleException;
import com.tinqin.zoostore.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public TagCreateResponse createTag(TagCreateRequest tagCreateRequest) {
        String title = tagCreateRequest.getTitle();

        if (checkIfTitleExists(title)) {
            throw new OccupiedTagTitleException();
        }

        Tag tagEntity = new Tag();
        tagEntity.setTitle(title);
        tagEntity.setIsArchived(Boolean.FALSE);

        this.tagRepository.save(tagEntity);

        return TagCreateResponse.
                builder().
                title(title).
                build();
    }

    @Override
    public TagUpdateResponse updateTag(TagUpdateRequest tagUpdateRequest) {
        String oldTitle = tagUpdateRequest.getOldTitle();
        String newTitle = tagUpdateRequest.getNewTitle();

        if (!checkIfTitleExists(oldTitle)) {
            throw new NoSuchTagException();
        }

        if (checkIfTitleExists(newTitle)) {
            throw new OccupiedTagTitleException();
        }

        Optional<Tag> tagByTitle = this.tagRepository.findTagByTitle(oldTitle);

        Tag updated = tagByTitle.get();
        updated.setTitle(newTitle);

        this.tagRepository.save(updated);

        return TagUpdateResponse.
                builder().
                title(newTitle).
                build();
    }

    @Override
    public boolean archiveTag(String title) {
        Tag tag = this.tagRepository.findTagByTitle(title).orElseThrow(NoSuchTagException::new);

        if (tag.getIsArchived()) {
            return false;
        }

        tag.setIsArchived(Boolean.TRUE);

        this.tagRepository.save(tag);

        return true;
    }

    @Override
    public boolean unarchiveTag(String title) {
        Tag tag = this.tagRepository.findTagByTitle(title).orElseThrow(NoSuchTagException::new);

        if (!tag.getIsArchived()) {
            return false;
        }

        tag.setIsArchived(Boolean.FALSE);

        this.tagRepository.save(tag);

        return true;
    }

    private boolean checkIfTitleExists(String title) {
        return this.tagRepository.findTagByTitle(title).isPresent();
    }
}
