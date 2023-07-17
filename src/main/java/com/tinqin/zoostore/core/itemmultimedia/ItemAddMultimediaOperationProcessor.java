package com.tinqin.zoostore.core.itemmultimedia;

import com.tinqin.zoostore.api.exception.item.NoSuchItemException;
import com.tinqin.zoostore.api.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaOperation;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaRequest;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Multimedia;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import com.tinqin.zoostore.persistence.repository.MultimediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ItemAddMultimediaOperationProcessor implements ItemAddMultimediaOperation {

    private final ItemRepository itemRepository;
    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemAddMultimediaOperationProcessor(ItemRepository itemRepository, MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemAddMultimediaResponse process(ItemAddMultimediaRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        Set<Multimedia> toAddToItem = item.getMultimedia();

        for (String currentId : input.getMultimediaId()) {
            UUID multimediaId = UUID.fromString(currentId);

            Multimedia multimedia = this.multimediaRepository.
                    findById(multimediaId).
                    orElseThrow(NoSuchMultimediaException::new);

            toAddToItem.add(multimedia);
        }

        Item itemWithAddedMultimedia = Item
                .builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .isArchived(item.getIsArchived())
                .vendor(item.getVendor())
                .tags(item.getTags())
                .multimedia(toAddToItem)
                .build();

        this.itemRepository.save(itemWithAddedMultimedia);

        return this.modelMapper.map(itemWithAddedMultimedia, ItemAddMultimediaResponse.class);
    }
}
