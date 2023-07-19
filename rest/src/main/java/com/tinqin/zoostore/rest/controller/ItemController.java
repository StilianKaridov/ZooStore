package com.tinqin.zoostore.rest.controller;

import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveOperation;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveRequest;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveResponse;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateOperation;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateRequest;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateResponse;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveOperation;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveRequest;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveResponse;
import com.tinqin.zoostore.api.operations.item.update.description.ItemUpdateDescriptionOperation;
import com.tinqin.zoostore.api.operations.item.update.description.ItemUpdateDescriptionRequest;
import com.tinqin.zoostore.api.operations.item.update.description.ItemUpdateDescriptionResponse;
import com.tinqin.zoostore.api.operations.item.update.title.ItemUpdateTitleOperation;
import com.tinqin.zoostore.api.operations.item.update.title.ItemUpdateTitleRequest;
import com.tinqin.zoostore.api.operations.item.update.title.ItemUpdateTitleResponse;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaOperation;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaRequest;
import com.tinqin.zoostore.api.operations.itemmultimedia.add.ItemAddMultimediaResponse;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaOperation;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaRequest;
import com.tinqin.zoostore.api.operations.itemmultimedia.remove.ItemRemoveMultimediaResponse;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagOperation;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagRequest;
import com.tinqin.zoostore.api.operations.itemtag.add.ItemAddTagResponse;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagOperation;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagRequest;
import com.tinqin.zoostore.api.operations.itemtag.remove.ItemRemoveTagResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private final ItemCreateOperation itemCreateOperation;
    private final ItemUpdateTitleOperation itemUpdateTitleOperation;
    private final ItemUpdateDescriptionOperation itemUpdateDescriptionOperation;
    private final ItemArchiveOperation itemArchiveOperation;
    private final ItemUnarchiveOperation itemUnarchiveOperation;
    private final ItemAddMultimediaOperation itemAddMultimediaOperation;
    private final ItemRemoveMultimediaOperation itemRemoveMultimediaOperation;
    private final ItemAddTagOperation itemAddTagOperation;
    private final ItemRemoveTagOperation itemRemoveTagOperation;

    @Autowired
    public ItemController(
            ItemCreateOperation itemCreateOperation,
            ItemUpdateTitleOperation itemUpdateTitleOperation,
            ItemUpdateDescriptionOperation itemUpdateDescriptionOperation,
            ItemArchiveOperation itemArchiveOperation,
            ItemUnarchiveOperation itemUnarchiveOperation,
            ItemAddMultimediaOperation itemAddMultimediaOperation,
            ItemRemoveMultimediaOperation itemRemoveMultimediaOperation,
            ItemAddTagOperation itemAddTagOperation,
            ItemRemoveTagOperation itemRemoveTagOperation
    ) {
        this.itemCreateOperation = itemCreateOperation;
        this.itemUpdateTitleOperation = itemUpdateTitleOperation;
        this.itemUpdateDescriptionOperation = itemUpdateDescriptionOperation;
        this.itemArchiveOperation = itemArchiveOperation;
        this.itemUnarchiveOperation = itemUnarchiveOperation;
        this.itemAddMultimediaOperation = itemAddMultimediaOperation;
        this.itemRemoveMultimediaOperation = itemRemoveMultimediaOperation;
        this.itemAddTagOperation = itemAddTagOperation;
        this.itemRemoveTagOperation = itemRemoveTagOperation;
    }


    @PostMapping("/createItem")
    public ResponseEntity<ItemCreateResponse> createItem(
            @Valid @RequestBody ItemCreateRequest itemCreateRequest
    ) {
        ItemCreateResponse createdItem = this.itemCreateOperation.process(itemCreateRequest);

        return ResponseEntity.ok(createdItem);
    }

    @PatchMapping("/editItemTitle")
    public ResponseEntity<ItemUpdateTitleResponse> updateItemTitle(
            @Valid @RequestBody ItemUpdateTitleRequest itemUpdateRequest
    ) {
        ItemUpdateTitleResponse updatedItem = this.itemUpdateTitleOperation.process(itemUpdateRequest);

        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/editItemDescription")
    public ResponseEntity<ItemUpdateDescriptionResponse> updateItemDescription(
            @Valid @RequestBody ItemUpdateDescriptionRequest itemUpdateRequest
    ) {
        ItemUpdateDescriptionResponse updatedItem = this.itemUpdateDescriptionOperation.process(itemUpdateRequest);

        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/archiveItem")
    public ResponseEntity<ItemArchiveResponse> archiveItem(
            @Valid @RequestBody ItemArchiveRequest itemArchiveRequest
    ) {
        ItemArchiveResponse archivedItem = this.itemArchiveOperation.process(itemArchiveRequest);

        return ResponseEntity.ok(archivedItem);
    }

    @PatchMapping("/unarchiveItem")
    public ResponseEntity<ItemUnarchiveResponse> unarchiveItem(
            @Valid @RequestBody ItemUnarchiveRequest itemUnarchiveRequest
    ) {
        ItemUnarchiveResponse unarchivedItem = this.itemUnarchiveOperation.process(itemUnarchiveRequest);

        return ResponseEntity.ok(unarchivedItem);
    }

    @PatchMapping("/addMultimediaToItem")
    public ResponseEntity<ItemAddMultimediaResponse> addMultimediaToItem(
            @Valid @RequestBody ItemAddMultimediaRequest itemAddMultimediaRequest
    ) {
        ItemAddMultimediaResponse item = this.itemAddMultimediaOperation.process(itemAddMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/removeMultimediaFromItem")
    public ResponseEntity<ItemRemoveMultimediaResponse> removeMultimediaFromItem(
            @Valid @RequestBody ItemRemoveMultimediaRequest itemRemoveMultimediaRequest
    ) {
        ItemRemoveMultimediaResponse item = this.itemRemoveMultimediaOperation.process(itemRemoveMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/addTagToItem")
    public ResponseEntity<ItemAddTagResponse> addTagToItem(
            @Valid @RequestBody ItemAddTagRequest itemAddTagRequest
    ) {
        ItemAddTagResponse item = this.itemAddTagOperation.process(itemAddTagRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/removeTagFromItem")
    public ResponseEntity<ItemRemoveTagResponse> removeTagFromItem(
            @Valid @RequestBody ItemRemoveTagRequest itemRemoveTagRequest
    ) {
        ItemRemoveTagResponse item = this.itemRemoveTagOperation.process(itemRemoveTagRequest);

        return ResponseEntity.ok(item);
    }
}
