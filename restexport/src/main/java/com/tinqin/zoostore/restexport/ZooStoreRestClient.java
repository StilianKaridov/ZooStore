package com.tinqin.zoostore.restexport;

import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveRequest;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveResponse;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateRequest;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateResponse;
import com.tinqin.zoostore.api.operations.item.get.GetItemByIdResponse;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveRequest;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveResponse;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateRequest;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateResponse;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaRequest;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaResponse;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaRequest;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaResponse;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagRequest;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagResponse;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagRequest;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

@Headers({"Content-Type: application/json"})
public interface ZooStoreRestClient {

    @RequestLine("GET /api/zoostore/items/{id}")
    GetItemByIdResponse getItemById(@Param String id);

    @RequestLine("POST /api/zoostore/items")
    ItemCreateResponse createItem(@RequestBody ItemCreateRequest itemCreateRequest);

    @RequestLine("PUT /api/zoostore/items")
    ItemUpdateResponse updateItem(@RequestBody ItemUpdateRequest itemUpdateRequest);

    @RequestLine("PATCH /api/zoostore/items/archive")
    ItemArchiveResponse archiveItem(@RequestBody ItemArchiveRequest itemArchiveRequest);

    @RequestLine("PATCH /api/zoostore/items/unarchive")
    ItemUnarchiveResponse unarchiveItem(@RequestBody ItemUnarchiveRequest itemUnarchiveRequest);

    @RequestLine("PATCH /api/zoostore/items/addMultimedia")
    ItemAddMultimediaResponse addMultimediaToItem(@RequestBody ItemAddMultimediaRequest itemAddMultimediaRequest);

    @RequestLine("PATCH /api/zoostore/items/removeMultimedia")
    ItemRemoveMultimediaResponse removeMultimediaFromItem(@RequestBody ItemRemoveMultimediaRequest itemRemoveMultimediaRequest);

    @RequestLine("PATCH /api/zoostore/items/addTag")
    ItemAddTagResponse addTagToItem(@RequestBody ItemAddTagRequest itemAddTagRequest);

    @RequestLine("PATCH /api/zoostore/items/removeTag")
    ItemRemoveTagResponse removeTagFromItem(@RequestBody ItemRemoveTagRequest itemRemoveTagRequest);
}
