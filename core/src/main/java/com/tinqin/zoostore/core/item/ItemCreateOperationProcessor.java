package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.core.exception.item.ItemAlreadyExistingException;
import com.tinqin.zoostore.core.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateOperation;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateRequest;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import com.tinqin.zoostore.persistence.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ItemCreateOperationProcessor implements ItemCreateOperation {

    private final ItemRepository itemRepository;
    private final VendorRepository vendorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemCreateOperationProcessor(ItemRepository itemRepository, VendorRepository vendorRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.vendorRepository = vendorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemCreateResponse process(ItemCreateRequest input) {
        Optional<Item> itemByTitle = this.itemRepository.findFirstByTitle(input.getTitle());

        if (itemByTitle.isPresent()) {
            throw new ItemAlreadyExistingException();
        }

        UUID id = UUID.fromString(input.getVendorId());
        Vendor vendor = this.vendorRepository
                .findById(id)
                .orElseThrow(NoSuchVendorException::new);

        Item item = Item
                .builder()
                .title(input.getTitle())
                .description(input.getDescription())
                .isArchived(Boolean.FALSE)
                .vendor(vendor)
                .build();
        this.itemRepository.save(item);

        return this.modelMapper.map(item, ItemCreateResponse.class);
    }
}
