package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.core.exception.item.ItemAlreadyExistingException;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.api.operations.item.update.title.ItemUpdateTitleOperation;
import com.tinqin.zoostore.api.operations.item.update.title.ItemUpdateTitleRequest;
import com.tinqin.zoostore.api.operations.item.update.title.ItemUpdateTitleResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemUpdateTitleOperationProcessor implements ItemUpdateTitleOperation {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemUpdateTitleOperationProcessor(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemUpdateTitleResponse process(ItemUpdateTitleRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        String newTitle = input.getNewTitle();

        Optional<Item> itemByTitle = this.itemRepository.findFirstByTitle(newTitle);

        if (itemByTitle.isPresent()) {
            throw new ItemAlreadyExistingException();
        }

        Item itemWithUpdatedTitle = Item
                .builder()
                .id(itemById.getId())
                .title(newTitle)
                .description(itemById.getDescription())
                .isArchived(itemById.getIsArchived())
                .vendor(itemById.getVendor())
                .tags(itemById.getTags())
                .multimedia(itemById.getMultimedia())
                .build();

        this.itemRepository.save(itemWithUpdatedTitle);

        return this.modelMapper.map(itemWithUpdatedTitle, ItemUpdateTitleResponse.class);
    }
}
