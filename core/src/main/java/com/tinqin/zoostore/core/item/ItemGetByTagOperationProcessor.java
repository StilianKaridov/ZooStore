package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagDataResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagOperation;
import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagRequest;
import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.MultimediaGetResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.TagGetResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.VendorGetResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.entity.Multimedia;
import com.tinqin.zoostore.persistence.entity.Tag;
import com.tinqin.zoostore.persistence.entity.Vendor;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItemGetByTagOperationProcessor implements ItemGetByTagOperation {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemGetByTagOperationProcessor(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public ItemGetByTagResponse process(ItemGetByTagRequest input) {
        Pageable pageable = PageRequest.of(input.getPageNumber(), input.getPageSize(), Sort.by("title"));

        List<Item> items = this.itemRepository.findAllByTags_Title(input.getTagTitle(), pageable);

        List<ItemGetByTagDataResponse> mappedItems = new ArrayList<>();

        for (Item i : items) {

            Set<MultimediaGetResponse> multimedia = new HashSet<>();
            for (Multimedia m : i.getMultimedia()) {
                multimedia.add(
                        MultimediaGetResponse
                                .builder()
                                .url(m.getUrl())
                                .build()
                );
            }

            Set<TagGetResponse> tags = new HashSet<>();
            for (Tag t : i.getTags()) {
                tags.add(
                        TagGetResponse
                                .builder()
                                .id(String.valueOf(t.getId()))
                                .title(t.getTitle())
                                .build()
                );
            }

            Vendor vendor = i.getVendor();

            VendorGetResponse mappedVendor = VendorGetResponse
                    .builder()
                    .id(String.valueOf(vendor.getId()))
                    .name(vendor.getName())
                    .phoneNumber(vendor.getPhoneNumber())
                    .build();

            mappedItems.add(
                    ItemGetByTagDataResponse
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

        return ItemGetByTagResponse
                .builder()
                .items(mappedItems)
                .build();
    }
}
