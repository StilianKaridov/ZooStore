package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.api.operations.item.get.GetItemByIdOperation;
import com.tinqin.zoostore.api.operations.item.get.GetItemByIdRequest;
import com.tinqin.zoostore.api.operations.item.get.GetItemByIdResponse;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetItemByIdOperationProcessor implements GetItemByIdOperation {

    private final ItemRepository itemRepository;

    @Autowired
    public GetItemByIdOperationProcessor(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public GetItemByIdResponse process(GetItemByIdRequest input) {
        UUID itemId = UUID.fromString(input.getId());

        Item item = this.itemRepository
                .getItemById(itemId)
                .orElseThrow(NoSuchItemException::new);

        if (item.getIsArchived()) {
            throw new NoSuchItemException();
        }

        return GetItemByIdResponse
                .builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .build();
    }
}
