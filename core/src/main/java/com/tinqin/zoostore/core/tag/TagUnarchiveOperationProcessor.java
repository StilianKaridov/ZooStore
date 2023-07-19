package com.tinqin.zoostore.core.tag;

import com.tinqin.zoostore.api.operations.tag.unarchive.TagUnarchiveOperation;
import com.tinqin.zoostore.api.operations.tag.unarchive.TagUnarchiveRequest;
import com.tinqin.zoostore.api.operations.tag.unarchive.TagUnarchiveResponse;
import com.tinqin.zoostore.core.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.core.exception.tag.TagAlreadyUnarchivedException;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagUnarchiveOperationProcessor implements TagUnarchiveOperation {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagUnarchiveOperationProcessor(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagUnarchiveResponse process(TagUnarchiveRequest input) {
        Tag tag = this.tagRepository.
                findTagByTitle(input.getTitle()).
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
}
