package com.tinqin.zoostore.core.itemmultimedia;

import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaOperation;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaRequest;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaResponse;
import com.tinqin.zoostore.core.exception.item.NoSuchItemException;
import com.tinqin.zoostore.core.exception.multimedia.MultimediaDoesntBelongItemException;
import com.tinqin.zoostore.core.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Multimedia;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import com.tinqin.zoostore.persistence.repository.MultimediaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ItemRemoveMultimediaOperationProcessor implements ItemRemoveMultimediaOperation {

    private final ItemRepository itemRepository;
    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemRemoveMultimediaOperationProcessor(ItemRepository itemRepository, MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemRemoveMultimediaResponse process(ItemRemoveMultimediaRequest input) {
        UUID id = UUID.fromString(input.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        input.getMultimediaId()
                .forEach(
                        currentId -> {
                            UUID multimediaId = UUID.fromString(currentId);
                            Multimedia multimedia = this.multimediaRepository
                                    .findById(multimediaId)
                                    .orElseThrow(NoSuchMultimediaException::new);

                            if (!item.getMultimedia().remove(multimedia)) {
                                throw new MultimediaDoesntBelongItemException(currentId);
                            }
                        });

        this.itemRepository.save(item);

        return this.modelMapper.map(item, ItemRemoveMultimediaResponse.class);
    }
}
