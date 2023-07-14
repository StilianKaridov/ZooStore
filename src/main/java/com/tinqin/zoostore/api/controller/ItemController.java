package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.item.ItemAddMultimediaRequest;
import com.tinqin.zoostore.api.request.item.ItemAddTagRequest;
import com.tinqin.zoostore.api.request.item.ItemArchiveRequest;
import com.tinqin.zoostore.api.request.item.ItemCreateRequest;
import com.tinqin.zoostore.api.request.item.ItemRemoveMultimediaRequest;
import com.tinqin.zoostore.api.request.item.ItemRemoveTagRequest;
import com.tinqin.zoostore.api.request.item.ItemUnarchiveRequest;
import com.tinqin.zoostore.api.request.item.ItemUpdateDescriptionRequest;
import com.tinqin.zoostore.api.request.item.ItemUpdateTitleRequest;
import com.tinqin.zoostore.api.response.item.ItemAddMultimediaResponse;
import com.tinqin.zoostore.api.response.item.ItemAddTagResponse;
import com.tinqin.zoostore.api.response.item.ItemArchiveResponse;
import com.tinqin.zoostore.api.response.item.ItemCreateResponse;
import com.tinqin.zoostore.api.response.item.ItemRemoveMultimediaResponse;
import com.tinqin.zoostore.api.response.item.ItemRemoveTagResponse;
import com.tinqin.zoostore.api.response.item.ItemUnarchiveResponse;
import com.tinqin.zoostore.api.response.item.ItemUpdateDescriptionResponse;
import com.tinqin.zoostore.api.response.item.ItemUpdateTitleResponse;
import com.tinqin.zoostore.service.ItemMultimediaService;
import com.tinqin.zoostore.service.ItemService;
import com.tinqin.zoostore.service.ItemTagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    private final ItemService itemService;
    private final ItemMultimediaService itemMultimediaService;
    private final ItemTagService itemTagService;

    @Autowired
    public ItemController(ItemService itemService, ItemMultimediaService itemMultimediaService, ItemTagService itemTagService) {
        this.itemService = itemService;
        this.itemMultimediaService = itemMultimediaService;
        this.itemTagService = itemTagService;
    }

    @PostMapping("/createItem")
    public ResponseEntity<ItemCreateResponse> createItem(
            @Valid @RequestBody ItemCreateRequest itemCreateRequest) {
        ItemCreateResponse createdItem = this.itemService.createItem(itemCreateRequest);

        return ResponseEntity.ok(createdItem);
    }

    @PatchMapping("/editItemTitle")
    public ResponseEntity<ItemUpdateTitleResponse> updateItemTitle(
            @Valid @RequestBody ItemUpdateTitleRequest itemUpdateRequest) {
        ItemUpdateTitleResponse updatedItem = this.itemService.updateItemTitle(itemUpdateRequest);

        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/editItemDescription")
    public ResponseEntity<ItemUpdateDescriptionResponse> updateItemDescription(
            @Valid @RequestBody ItemUpdateDescriptionRequest itemUpdateRequest) {
        ItemUpdateDescriptionResponse updatedItem = this.itemService.updateItemDescription(itemUpdateRequest);

        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/archiveItem")
    public ResponseEntity<ItemArchiveResponse> archiveItem(
            @Valid @RequestBody ItemArchiveRequest itemArchiveRequest) {
        ItemArchiveResponse archivedItem = this.itemService.archiveItem(itemArchiveRequest);

        return ResponseEntity.ok(archivedItem);
    }

    @PatchMapping("/unarchiveItem")
    public ResponseEntity<ItemUnarchiveResponse> unarchiveItem(
            @Valid @RequestBody ItemUnarchiveRequest itemUnarchiveRequest) {
        ItemUnarchiveResponse unarchivedItem = this.itemService.unarchiveItem(itemUnarchiveRequest);

        return ResponseEntity.ok(unarchivedItem);
    }

    @PatchMapping("/addMultimediaToItem")
    public ResponseEntity<ItemAddMultimediaResponse> addMultimediaToItem(
            @Valid @RequestBody ItemAddMultimediaRequest itemAddMultimediaRequest) {
        ItemAddMultimediaResponse item = this.itemMultimediaService.addMultimediaToItem(itemAddMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/removeMultimediaFromItem")
    public ResponseEntity<ItemRemoveMultimediaResponse> removeMultimediaFromItem(
            @Valid @RequestBody ItemRemoveMultimediaRequest itemRemoveMultimediaRequest) {
        ItemRemoveMultimediaResponse item = this.itemMultimediaService.removeMultimediaFromItem(itemRemoveMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/addTagToItem")
    public ResponseEntity<ItemAddTagResponse> addTagToItem(
            @Valid @RequestBody ItemAddTagRequest itemAddTagRequest) {
        ItemAddTagResponse item = this.itemTagService.addTagToItem(itemAddTagRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/removeTagFromItem")
    public ResponseEntity<ItemRemoveTagResponse> removeTagFromItem(
            @Valid @RequestBody ItemRemoveTagRequest itemRemoveTagRequest) {
        ItemRemoveTagResponse item = this.itemTagService.removeTagFromItem(itemRemoveTagRequest);

        return ResponseEntity.ok(item);
    }
}
