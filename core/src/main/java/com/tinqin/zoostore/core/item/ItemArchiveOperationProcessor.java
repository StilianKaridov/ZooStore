package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.core.exception.item.ItemAlreadyArchivedException;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveOperation;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveRequest;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemArchiveOperationProcessor implements ItemArchiveOperation {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemArchiveOperationProcessor(ItemRepository itemRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemArchiveResponse process(ItemArchiveRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        if (itemById.getIsArchived()) {
            throw new ItemAlreadyArchivedException();
        }

        Item archivedItem = Item
                .builder()
                .id(itemById.getId())
                .title(itemById.getTitle())
                .description(itemById.getDescription())
                .isArchived(Boolean.TRUE)
                .vendor(itemById.getVendor())
                .tags(itemById.getTags())
                .multimedia(itemById.getMultimedia())
                .build();

        this.itemRepository.save(archivedItem);

        return this.modelMapper.map(archivedItem, ItemArchiveResponse.class);
    }
}
