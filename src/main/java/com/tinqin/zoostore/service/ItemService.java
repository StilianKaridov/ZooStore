package com.tinqin.zoostore.service;

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

public interface ItemService {

    ItemCreateResponse createItem(ItemCreateRequest itemCreateRequest);

    ItemUpdateTitleResponse updateItemTitle(ItemUpdateTitleRequest itemUpdateTitleRequest);

    ItemUpdateDescriptionResponse updateItemDescription(ItemUpdateDescriptionRequest itemUpdateRequest);

    ItemArchiveResponse archiveItem(ItemArchiveRequest itemArchiveRequest);

    ItemUnarchiveResponse unarchiveItem(ItemUnarchiveRequest itemUnarchiveRequest);
}
