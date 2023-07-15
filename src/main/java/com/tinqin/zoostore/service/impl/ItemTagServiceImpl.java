package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.item.ItemAddTagRequest;
import com.tinqin.zoostore.api.request.item.ItemRemoveTagRequest;
import com.tinqin.zoostore.api.response.item.ItemAddTagResponse;
import com.tinqin.zoostore.api.response.item.ItemRemoveTagResponse;
import com.tinqin.zoostore.data.entity.Item;
import com.tinqin.zoostore.data.entity.Tag;
import com.tinqin.zoostore.data.repository.ItemRepository;
import com.tinqin.zoostore.data.repository.TagRepository;
import com.tinqin.zoostore.exception.item.NoSuchItemException;
import com.tinqin.zoostore.exception.tag.NoSuchTagException;
import com.tinqin.zoostore.exception.tag.TagDoesntBelongToItemException;
import com.tinqin.zoostore.service.ItemTagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ItemTagServiceImpl implements ItemTagService {

    private final ItemRepository itemRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemTagServiceImpl(ItemRepository itemRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemAddTagResponse addTagToItem(ItemAddTagRequest itemAddTagRequest) {
        UUID id = UUID.fromString(itemAddTagRequest.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        Set<Tag> toAddToItem = item.getTags();

        for (String currentId : itemAddTagRequest.getTagsId()) {
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

    @Override
    public ItemRemoveTagResponse removeTagFromItem(ItemRemoveTagRequest itemRemoveTagRequest) {
        UUID id = UUID.fromString(itemRemoveTagRequest.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        for (String currentId : itemRemoveTagRequest.getTagsId()) {
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
