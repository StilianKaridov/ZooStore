package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetDataResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.MultimediaGetResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.TagGetResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.VendorGetResponse;
import com.tinqin.zoostore.api.operations.item.getbytitle.ItemGetByTitleOperation;
import com.tinqin.zoostore.api.operations.item.getbytitle.ItemGetByTitleRequest;
import com.tinqin.zoostore.api.operations.item.getbytitle.ItemGetByTitleResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemGetByTitleOperationProcessor implements ItemGetByTitleOperation {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemGetByTitleOperationProcessor(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemGetByTitleResponse process(ItemGetByTitleRequest input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize());

        Page<Item> items = this.itemRepository.findAllByPartialTitle(input.getTitle(), pageable);

        List<ItemGetDataResponse> mappedItems = new ArrayList<>();

        for (Item i : items) {

            Set<MultimediaGetResponse> multimedia = i.getMultimedia()
                    .stream()
                    .map(
                            m -> MultimediaGetResponse
                                    .builder()
                                    .url(m.getUrl())
                                    .build()
                    ).collect(Collectors.toSet());

            Set<TagGetResponse> tags = i.getTags()
                    .stream()
                    .map(
                            t -> TagGetResponse
                                    .builder()
                                    .id(String.valueOf(t.getId()))
                                    .title(t.getTitle())
                                    .build()
                    ).collect(Collectors.toSet());

            Vendor vendor = i.getVendor();

            VendorGetResponse mappedVendor = VendorGetResponse
                    .builder()
                    .id(String.valueOf(vendor.getId()))
                    .name(vendor.getName())
                    .phoneNumber(vendor.getPhoneNumber())
                    .build();

            mappedItems.add(
                    ItemGetDataResponse
                            .builder()
                            .id(String.valueOf(i.getId()))
                            .title(i.getTitle())
                            .description(i.getDescription())
                            .vendor(mappedVendor)
                            .multimedia(multimedia)
                            .tags(tags)
                            .build()
            );
        }

        return ItemGetByTitleResponse
                .builder()
                .items(mappedItems)
                .totalItems(items.getTotalElements())
                .page(items.getNumber())
                .limit(items.getSize())
                .build();
    }
}
