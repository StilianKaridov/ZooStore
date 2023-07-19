package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.api.operations.item.update.description.ItemUpdateDescriptionOperation;
import com.tinqin.zoostore.api.operations.item.update.description.ItemUpdateDescriptionRequest;
import com.tinqin.zoostore.api.operations.item.update.description.ItemUpdateDescriptionResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemUpdateDescriptionOperationProcessor implements ItemUpdateDescriptionOperation {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemUpdateDescriptionOperationProcessor(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemUpdateDescriptionResponse process(ItemUpdateDescriptionRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        String newDescription = input.getNewDescription();

        Item itemWithUpdatedDescription = Item
                .builder()
                .id(itemById.getId())
                .title(itemById.getTitle())
                .description(newDescription)
                .isArchived(itemById.getIsArchived())
                .vendor(itemById.getVendor())
                .tags(itemById.getTags())
                .multimedia(itemById.getMultimedia())
                .build();

        this.itemRepository.save(itemWithUpdatedDescription);

        return this.modelMapper.map(itemWithUpdatedDescription, ItemUpdateDescriptionResponse.class);
    }
}
