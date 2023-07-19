package com.tinqin.zoostore.core.tag;

import com.tinqin.zoostore.api.operations.tag.archive.TagArchiveOperation;
import com.tinqin.zoostore.api.operations.tag.archive.TagArchiveRequest;
import com.tinqin.zoostore.api.operations.tag.archive.TagArchiveResponse;
import com.tinqin.zoostore.core.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.core.exception.tag.TagAlreadyArchivedException;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagArchiveOperationProcessor implements TagArchiveOperation {

    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TagArchiveOperationProcessor(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagArchiveResponse process(TagArchiveRequest input) {
        Tag tag = this.tagRepository.
                findTagByTitle(input.getTitle()).
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
}
