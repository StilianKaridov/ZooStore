package com.tinqin.zoostore.core.tag;

import com.tinqin.zoostore.api.operations.tag.update.TagUpdateOperation;
import com.tinqin.zoostore.api.operations.tag.update.TagUpdateRequest;
import com.tinqin.zoostore.api.operations.tag.update.TagUpdateResponse;
import com.tinqin.zoostore.core.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import com.tinqin.zoostore.core.exception.tag.OccupiedTagTitleException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagUpdateOperationProcessor implements TagUpdateOperation {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagUpdateOperationProcessor(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagUpdateResponse process(TagUpdateRequest input) {
        String oldTitle = input.getOldTitle();
        String newTitle = input.getNewTitle();

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

    private boolean checkIfTitleExists(String title) {
        return this.tagRepository.findTagByTitle(title).isPresent();
    }
}
