package com.tinqin.zoostore.core.itemtag;

import com.tinqin.zoostore.api.exception.item.NoSuchItemException;
import com.tinqin.zoostore.api.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagOperation;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagRequest;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ItemAddTagOperationProcessor implements ItemAddTagOperation {

    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemAddTagOperationProcessor(ItemRepository itemRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemAddTagResponse process(ItemAddTagRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        Set<Tag> toAddToItem = item.getTags();

        for (String currentId : input.getTagsId()) {
            UUID tagId = UUID.fromString(currentId);

            Tag tag = this.tagRepository.
                    findById(tagId).
                    orElseThrow(NoSuchTagException::new);

            toAddToItem.add(tag);
        }

        Item itemWithUpdatedTags = Item
                .builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .isArchived(item.getIsArchived())
                .vendor(item.getVendor())
                .tags(toAddToItem)
                .multimedia(item.getMultimedia())
                .build();

        this.itemRepository.save(itemWithUpdatedTags);

        return this.modelMapper.map(itemWithUpdatedTags, ItemAddTagResponse.class);
    }
}
