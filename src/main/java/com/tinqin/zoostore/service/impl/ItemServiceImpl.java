package com.tinqin.zoostore.service.impl;

import com.tinqin.zoostore.api.request.item.ItemArchiveRequest;
import com.tinqin.zoostore.api.request.item.ItemCreateRequest;
import com.tinqin.zoostore.api.request.item.ItemUnarchiveRequest;
import com.tinqin.zoostore.api.request.item.ItemUpdateDescriptionRequest;
import com.tinqin.zoostore.api.request.item.ItemUpdateTitleRequest;
import com.tinqin.zoostore.api.response.item.ItemArchiveResponse;
import com.tinqin.zoostore.api.response.item.ItemCreateResponse;
import com.tinqin.zoostore.api.response.item.ItemUnarchiveResponse;
import com.tinqin.zoostore.api.response.item.ItemUpdateDescriptionResponse;
import com.tinqin.zoostore.api.response.item.ItemUpdateTitleResponse;
import com.tinqin.zoostore.data.entity.Item;
import com.tinqin.zoostore.data.entity.Vendor;
import com.tinqin.zoostore.data.repository.ItemRepository;
import com.tinqin.zoostore.exception.item.ItemAlreadyArchivedException;
import com.tinqin.zoostore.exception.item.ItemAlreadyExistingException;
import com.tinqin.zoostore.exception.item.ItemAlreadyUnarchivedException;
import com.tinqin.zoostore.exception.item.NoSuchItemException;
import com.tinqin.zoostore.service.ItemService;
import com.tinqin.zoostore.service.VendorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final VendorService vendorService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, VendorService vendorService, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.vendorService = vendorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemCreateResponse createItem(ItemCreateRequest itemCreateRequest) {
        Optional<Item> itemByTitle = this.itemRepository.findFirstByTitle(itemCreateRequest.getTitle());

        if (itemByTitle.isPresent()) {
            throw new ItemAlreadyExistingException();
        }

        Item item = new Item();

        item.setTitle(itemCreateRequest.getTitle());
        item.setDescription(itemCreateRequest.getDescription());
        item.setIsArchived(Boolean.FALSE);

        UUID id = UUID.fromString(itemCreateRequest.getVendorId());

        Vendor vendor = this.vendorService.getVendorById(id);
        item.setVendor(vendor);

        this.itemRepository.save(item);

        return this.modelMapper.map(item, ItemCreateResponse.class);
    }

    @Override
    public ItemUpdateTitleResponse updateItemTitle(ItemUpdateTitleRequest itemUpdateTitleRequest) {
        UUID id = UUID.fromString(itemUpdateTitleRequest.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        Optional<Item> itemByTitle = this.itemRepository.findFirstByTitle(itemUpdateTitleRequest.getNewTitle());

        if (itemByTitle.isPresent()) {
            throw new ItemAlreadyExistingException();
        }

        String newTitle = itemUpdateTitleRequest.getNewTitle();

        itemById.setTitle(newTitle);

        this.itemRepository.save(itemById);

        return this.modelMapper.map(itemById, ItemUpdateTitleResponse.class);
    }

    @Override
    public ItemUpdateDescriptionResponse updateItemDescription(ItemUpdateDescriptionRequest itemUpdateRequest) {
        UUID id = UUID.fromString(itemUpdateRequest.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        String newDescription = itemUpdateRequest.getNewDescription();

        itemById.setDescription(newDescription);

        this.itemRepository.save(itemById);

        return this.modelMapper.map(itemById, ItemUpdateDescriptionResponse.class);
    }

    @Override
    public ItemArchiveResponse archiveItem(ItemArchiveRequest itemArchiveRequest) {
        UUID id = UUID.fromString(itemArchiveRequest.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        if (itemById.getIsArchived()) {
            throw new ItemAlreadyArchivedException();
        }

        itemById.setIsArchived(Boolean.TRUE);

        this.itemRepository.save(itemById);

        return this.modelMapper.map(itemById, ItemArchiveResponse.class);
    }

    @Override
    public ItemUnarchiveResponse unarchiveItem(ItemUnarchiveRequest itemUnarchiveRequest) {
        UUID id = UUID.fromString(itemUnarchiveRequest.getId());

        Item itemById = this.itemRepository.
                findById(id)
                .orElseThrow(NoSuchItemException::new);

        if (!itemById.getIsArchived()) {
            throw new ItemAlreadyUnarchivedException();
        }

        itemById.setIsArchived(Boolean.FALSE);

        this.itemRepository.save(itemById);

        return this.modelMapper.map(itemById, ItemUnarchiveResponse.class);
    }
}
