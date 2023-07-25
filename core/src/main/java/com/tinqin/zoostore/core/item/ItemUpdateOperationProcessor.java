package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.api.operations.item.update.ItemUpdateOperation;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateRequest;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateResponse;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemUpdateOperationProcessor implements ItemUpdateOperation {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemUpdateOperationProcessor(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemUpdateResponse process(ItemUpdateRequest input) {
        UUID id = UUID.fromString(input.getItemId());

        Item item = this.itemRepository.getItemById(id)
                .orElseThrow(NoSuchItemException::new);

        Optional<String> title = input.getTitle();
        title.ifPresent(item::setTitle);

        Optional<String> description = input.getDescription();
        description.ifPresent(item::setDescription);

        Item updatedItem = this.itemRepository.save(item);

        return ItemUpdateResponse
                .builder()
                .itemId(String.valueOf(id))
                .title(updatedItem.getTitle())
                .description(updatedItem.getDescription())
                .build();
    }
}
