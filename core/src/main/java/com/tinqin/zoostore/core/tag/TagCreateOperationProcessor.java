package com.tinqin.zoostore.core.tag;

import com.tinqin.zoostore.api.operations.tag.create.TagCreateOperation;
import com.tinqin.zoostore.api.operations.tag.create.TagCreateRequest;
import com.tinqin.zoostore.api.operations.tag.create.TagCreateResponse;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import com.tinqin.zoostore.core.exception.tag.OccupiedTagTitleException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagCreateOperationProcessor implements TagCreateOperation {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagCreateOperationProcessor(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagCreateResponse process(TagCreateRequest input) {
        String title = input.getTitle();

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

    private boolean checkIfTitleExists(String title) {
        return this.tagRepository.findTagByTitle(title).isPresent();
    }
}
