package com.tinqin.zoostore.core.item;

import com.tinqin.zoostore.api.operations.item.getbytag.VendorGetResponse;
import com.tinqin.zoostore.api.operations.item.getlistofitems.GetListOfItemsOperation;
import com.tinqin.zoostore.api.operations.item.getlistofitems.GetListOfItemsRequest;
import com.tinqin.zoostore.api.operations.item.getlistofitems.GetListOfItemsResponse;
import com.tinqin.zoostore.api.operations.item.getlistofitems.ItemGetListOfItemsDataResponse;
import com.tinqin.zoostore.persistence.entity.Item;
import com.tinqin.zoostore.persistence.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetListOfItemsOperationProcessor implements GetListOfItemsOperation {

    private final ItemRepository itemRepository;

    @Autowired
    public GetListOfItemsOperationProcessor(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public GetListOfItemsResponse process(GetListOfItemsRequest input) {
        List<UUID> uuids = input
                .getIds()
                .stream()
                .map(UUID::fromString)
                .toList();

        List<Item> items = this.itemRepository.findAllByIdIn(uuids);

        List<ItemGetListOfItemsDataResponse> mappedItems = items
                .stream()
                .map(
                        i -> ItemGetListOfItemsDataResponse
                                .builder()
                                .id(i.getId().toString())
                                .title(i.getTitle())
                                .vendor(
                                        VendorGetResponse
                                        .builder()
                                        .id(i.getVendor().getId().toString())
                                        .name(i.getVendor().getName())
                                        .phoneNumber(i.getVendor().getPhoneNumber())
                                        .build()
                                )
                                .build()
                ).toList();

        return GetListOfItemsResponse
                .builder()
                .items(mappedItems)
                .build();
    }
}
