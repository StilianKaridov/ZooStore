package com.tinqin.zoostore.core.itemtag;

import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.core.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.core.exception.tag.TagDoesntBelongToItemException;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagOperation;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagRequest;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import com.tinqin.zoostore.persistence.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemRemoveTagOperationProcessor implements ItemRemoveTagOperation {

    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemRemoveTagOperationProcessor(ItemRepository itemRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemRemoveTagResponse process(ItemRemoveTagRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        for (String currentId : input.getTagsId()) {
            UUID tagId = UUID.fromString(currentId);

            Tag tag = this.tagRepository.
                    findById(tagId).
                    orElseThrow(NoSuchTagException::new);

            if (!item.getTags().remove(tag)) {
                throw new TagDoesntBelongToItemException(currentId);
            }
        }

        this.itemRepository.save(item);

        return this.modelMapper.map(item, ItemRemoveTagResponse.class);
    }
}
