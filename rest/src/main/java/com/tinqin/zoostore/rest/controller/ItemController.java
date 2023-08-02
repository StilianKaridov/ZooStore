package com.tinqin.zoostore.rest.controller;

import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveOperation;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveRequest;
import com.tinqin.zoostore.api.operations.item.archive.ItemArchiveResponse;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateOperation;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateRequest;
import com.tinqin.zoostore.api.operations.item.create.ItemCreateResponse;
import com.tinqin.zoostore.api.operations.item.get.GetItemByIdOperation;
import com.tinqin.zoostore.api.operations.item.get.GetItemByIdRequest;
import com.tinqin.zoostore.api.operations.item.get.GetItemByIdResponse;
import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagOperation;
import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagRequest;
import com.tinqin.zoostore.api.operations.item.getbytag.ItemGetByTagResponse;
import com.tinqin.zoostore.api.operations.item.getlistofitems.GetListOfItemsOperation;
import com.tinqin.zoostore.api.operations.item.getlistofitems.GetListOfItemsRequest;
import com.tinqin.zoostore.api.operations.item.getlistofitems.GetListOfItemsResponse;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveOperation;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveRequest;
import com.tinqin.zoostore.api.operations.item.unarchive.ItemUnarchiveResponse;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateOperation;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateRequest;
import com.tinqin.zoostore.api.operations.item.update.ItemUpdateResponse;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zoostore/items")
@Validated
public class ItemController {

    private final ItemCreateOperation itemCreateOperation;
    private final ItemArchiveOperation itemArchiveOperation;
    private final ItemUnarchiveOperation itemUnarchiveOperation;
    private final ItemAddMultimediaOperation itemAddMultimediaOperation;
    private final ItemRemoveMultimediaOperation itemRemoveMultimediaOperation;
    private final ItemAddTagOperation itemAddTagOperation;
    private final ItemRemoveTagOperation itemRemoveTagOperation;
    private final GetItemByIdOperation getItemByIdOperation;
    private final ItemUpdateOperation itemUpdateOperation;
    private final ItemGetByTagOperation itemGetByTagOperation;
    private final GetListOfItemsOperation getListOfItemsOperation;

    @Autowired
    public ItemController(
            ItemCreateOperation itemCreateOperation,
            ItemArchiveOperation itemArchiveOperation,
            ItemUnarchiveOperation itemUnarchiveOperation,
            ItemAddMultimediaOperation itemAddMultimediaOperation,
            ItemRemoveMultimediaOperation itemRemoveMultimediaOperation,
            ItemAddTagOperation itemAddTagOperation,
            ItemRemoveTagOperation itemRemoveTagOperation,
            GetItemByIdOperation getItemByIdOperation, ItemUpdateOperation itemUpdateOperation, ItemGetByTagOperation itemGetByTagOperation, GetListOfItemsOperation getListOfItemsOperation) {
        this.itemCreateOperation = itemCreateOperation;
        this.itemArchiveOperation = itemArchiveOperation;
        this.itemUnarchiveOperation = itemUnarchiveOperation;
        this.itemAddMultimediaOperation = itemAddMultimediaOperation;
        this.itemRemoveMultimediaOperation = itemRemoveMultimediaOperation;
        this.itemAddTagOperation = itemAddTagOperation;
        this.itemRemoveTagOperation = itemRemoveTagOperation;
        this.getItemByIdOperation = getItemByIdOperation;
        this.itemUpdateOperation = itemUpdateOperation;
        this.itemGetByTagOperation = itemGetByTagOperation;
        this.getListOfItemsOperation = getListOfItemsOperation;
    }

    @GetMapping
    public ResponseEntity<ItemGetByTagResponse> getItemsByTagTitle(
            @RequestParam @NotBlank(message = "Title is required.") String title,
            @RequestParam @Min(value = 1, message = "Page number must be positive number.") Integer pageNumber,
            @RequestParam @Min(value = 1, message = "Page size must be positive number.") Integer pageSize
    ) {
        ItemGetByTagRequest itemRequest = ItemGetByTagRequest
                .builder()
                .tagTitle(title)
                .pageNumber(pageNumber - 1)
                .pageSize(pageSize)
                .build();

        ItemGetByTagResponse response = this.itemGetByTagOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetItemByIdResponse> getItemById(@PathVariable String id) {
        GetItemByIdRequest itemRequest = GetItemByIdRequest
                .builder()
                .id(id)
                .build();

        GetItemByIdResponse response = this.getItemByIdOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/list")
    public ResponseEntity<GetListOfItemsResponse> getListOfItemsByIds(
            @RequestBody GetListOfItemsRequest getListOfItemsRequest
    ) {
        GetListOfItemsResponse response = this.getListOfItemsOperation.process(getListOfItemsRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ItemCreateResponse> createItem(
            @Valid @RequestBody ItemCreateRequest itemCreateRequest
    ) {
        ItemCreateResponse createdItem = this.itemCreateOperation.process(itemCreateRequest);

        return ResponseEntity.ok(createdItem);
    }

    @PutMapping
    public ResponseEntity<ItemUpdateResponse> updateItem(
            @Valid @RequestBody ItemUpdateRequest itemUpdateRequest
    ) {
        ItemUpdateResponse updatedItem = this.itemUpdateOperation.process(itemUpdateRequest);

        return ResponseEntity.ok(updatedItem);
    }

    @PatchMapping("/archive")
    public ResponseEntity<ItemArchiveResponse> archiveItem(
            @Valid @RequestBody ItemArchiveRequest itemArchiveRequest
    ) {
        ItemArchiveResponse archivedItem = this.itemArchiveOperation.process(itemArchiveRequest);

        return ResponseEntity.ok(archivedItem);
    }

    @PatchMapping("/unarchive")
    public ResponseEntity<ItemUnarchiveResponse> unarchiveItem(
            @Valid @RequestBody ItemUnarchiveRequest itemUnarchiveRequest
    ) {
        ItemUnarchiveResponse unarchivedItem = this.itemUnarchiveOperation.process(itemUnarchiveRequest);

        return ResponseEntity.ok(unarchivedItem);
    }

    @PatchMapping("/addMultimedia")
    public ResponseEntity<ItemAddMultimediaResponse> addMultimediaToItem(
            @Valid @RequestBody ItemAddMultimediaRequest itemAddMultimediaRequest
    ) {
        ItemAddMultimediaResponse item = this.itemAddMultimediaOperation.process(itemAddMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/removeMultimedia")
    public ResponseEntity<ItemRemoveMultimediaResponse> removeMultimediaFromItem(
            @Valid @RequestBody ItemRemoveMultimediaRequest itemRemoveMultimediaRequest
    ) {
        ItemRemoveMultimediaResponse item = this.itemRemoveMultimediaOperation.process(itemRemoveMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/addTag")
    public ResponseEntity<ItemAddTagResponse> addTagToItem(
            @Valid @RequestBody ItemAddTagRequest itemAddTagRequest
    ) {
        ItemAddTagResponse item = this.itemAddTagOperation.process(itemAddTagRequest);

        return ResponseEntity.ok(item);
    }

    @PatchMapping("/removeTag")
    public ResponseEntity<ItemRemoveTagResponse> removeTagFromItem(
            @Valid @RequestBody ItemRemoveTagRequest itemRemoveTagRequest
    ) {
        ItemRemoveTagResponse item = this.itemRemoveTagOperation.process(itemRemoveTagRequest);

        return ResponseEntity.ok(item);
    }
}
