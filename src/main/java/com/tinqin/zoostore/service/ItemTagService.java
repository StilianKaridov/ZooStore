package com.tinqin.zoostore.service;

import com.tinqin.zoostore.api.request.item.ItemAddTagRequest;
import com.tinqin.zoostore.api.request.item.ItemRemoveTagRequest;
import com.tinqin.zoostore.api.response.item.ItemAddTagResponse;
import com.tinqin.zoostore.api.response.item.ItemRemoveTagResponse;

public interface ItemTagService {
    ItemAddTagResponse addTagToItem(ItemAddTagRequest itemAddTagRequest);

    ItemRemoveTagResponse removeTagFromItem(ItemRemoveTagRequest itemRemoveTagRequest);
}
