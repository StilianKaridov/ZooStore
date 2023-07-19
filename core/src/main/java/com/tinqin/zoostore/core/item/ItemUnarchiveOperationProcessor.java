package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.core.exception.item.ItemAlreadyUnarchivedException;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveOperation;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveRequest;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemUnarchiveOperationProcessor implements ItemUnarchiveOperation {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemUnarchiveOperationProcessor(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemUnarchiveResponse process(ItemUnarchiveRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        if (!itemById.getIsArchived()) {
            throw new ItemAlreadyUnarchivedException();
        }

        Item unarchivedItem = Item
                .builder()
                .id(itemById.getId())
                .title(itemById.getTitle())
                .description(itemById.getDescription())
                .isArchived(Boolean.FALSE)
                .vendor(itemById.getVendor())
                .tags(itemById.getTags())
                .multimedia(itemById.getMultimedia())
                .build();

        this.itemRepository.save(unarchivedItem);

        return this.modelMapper.map(unarchivedItem, ItemUnarchiveResponse.class);
    }
}
