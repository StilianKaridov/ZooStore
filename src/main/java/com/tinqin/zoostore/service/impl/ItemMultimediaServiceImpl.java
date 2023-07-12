package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.item.ItemAddMultimediaRequest;
import com.tinqin.zoostore.api.request.item.ItemRemoveMultimediaRequest;
import com.tinqin.zoostore.api.response.item.ItemAddMultimediaResponse;
import com.tinqin.zoostore.api.response.item.ItemRemoveMultimediaResponse;
import com.tinqin.zoostore.data.entity.Item;
import com.tinqin.zoostore.data.entity.Multimedia;
import com.tinqin.zoostore.data.repository.ItemRepository;
import com.tinqin.zoostore.data.repository.MultimediaRepository;
import com.tinqin.zoostore.exception.multimedia.MultimediaDoesntBelongItemException;
import com.tinqin.zoostore.exception.item.NoSuchItemException;
import com.tinqin.zoostore.exception.multimedia.NoSuchMultimediaException;
import com.tinqin.zoostore.service.ItemMultimediaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ItemMultimediaServiceImpl implements ItemMultimediaService {

    private final ItemRepository itemRepository;
    private final MultimediaRepository multimediaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemMultimediaServiceImpl(ItemRepository itemRepository, MultimediaRepository multimediaRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.multimediaRepository = multimediaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemAddMultimediaResponse addMultimediaToItem(ItemAddMultimediaRequest itemAddMultimediaRequest) {
        UUID id = UUID.fromString(itemAddMultimediaRequest.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        Set<Multimedia> toAddToItem = item.getMultimedia();

        for (String currentId : itemAddMultimediaRequest.getMultimediaId()) {
            UUID multimediaId = UUID.fromString(currentId);

            Multimedia multimedia = this.multimediaRepository.
                    findById(multimediaId).
                    orElseThrow(NoSuchMultimediaException::new);

            toAddToItem.add(multimedia);
        }

        item.setMultimedia(toAddToItem);

        this.itemRepository.save(item);

        return this.modelMapper.map(item, ItemAddMultimediaResponse.class);
    }

    @Override
    public ItemRemoveMultimediaResponse removeMultimediaFromItem(ItemRemoveMultimediaRequest itemRemoveMultimediaRequest) {
        UUID id = UUID.fromString(itemRemoveMultimediaRequest.getId());

        Item item = this.itemRepository.
                findById(id).
                orElseThrow(NoSuchItemException::new);

        for (String currentId : itemRemoveMultimediaRequest.getMultimediaId()) {
            UUID multimediaId = UUID.fromString(currentId);

            Multimedia multimedia = this.multimediaRepository.
                    findById(multimediaId).
                    orElseThrow(NoSuchMultimediaException::new);

            if (!item.getMultimedia().remove(multimedia)) {
                throw new MultimediaDoesntBelongItemException(currentId);
            }
        }

        this.itemRepository.save(item);

        return this.modelMapper.map(item, ItemRemoveMultimediaResponse.class);
    }


}
