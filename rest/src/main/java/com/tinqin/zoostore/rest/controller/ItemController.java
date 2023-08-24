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
import com.tinqin.zoostore.api.operations.item.getbytitle.ItemGetByTitleOperation;
import com.tinqin.zoostore.api.operations.item.getbytitle.ItemGetByTitleRequest;
import com.tinqin.zoostore.api.operations.item.getbytitle.ItemGetByTitleResponse;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final ItemGetByTitleOperation itemGetByTitleOperation;

    @Autowired
    public ItemController(
            ItemCreateOperation itemCreateOperation,
            ItemArchiveOperation itemArchiveOperation,
            ItemUnarchiveOperation itemUnarchiveOperation,
            ItemAddMultimediaOperation itemAddMultimediaOperation,
            ItemRemoveMultimediaOperation itemRemoveMultimediaOperation,
            ItemAddTagOperation itemAddTagOperation,
            ItemRemoveTagOperation itemRemoveTagOperation,
            GetItemByIdOperation getItemByIdOperation, ItemUpdateOperation itemUpdateOperation, ItemGetByTagOperation itemGetByTagOperation, GetListOfItemsOperation getListOfItemsOperation, ItemGetByTitleOperation itemGetByTitleOperation) {
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
        this.itemGetByTitleOperation = itemGetByTitleOperation;
    }

    @Operation(description = "Gets items by having the specified tag title. Paginates the result.",
            summary = "Gets items by tag title.")
    @ApiResponse(responseCode = "200", description = "Items found.")
    @ApiResponse(responseCode = "400",
            description = "Title must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "Title is required."), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid page number.",
            content = {@Content(examples = @ExampleObject(value = "Page number must be greater than or equal to zero."), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid page size.",
            content = {@Content(examples = @ExampleObject(value = "Page size must be positive number."), mediaType = "text/html")})
    @GetMapping("/byTag")
    public ResponseEntity<ItemGetByTagResponse> getItemsByTagTitle(
            @RequestParam @NotBlank(message = "Title is required.") String title,
            @RequestParam @Min(value = 0, message = "Page number must be greater than or equal to zero.") Integer pageNumber,
            @RequestParam @Min(value = 1, message = "Page size must be positive number.") Integer pageSize
    ) {
        ItemGetByTagRequest itemRequest = ItemGetByTagRequest
                .builder()
                .tagTitle(title)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        ItemGetByTagResponse response = this.itemGetByTagOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Gets items by having the specified item title. Paginates the result.",
            summary = "Gets items by item title.")
    @ApiResponse(responseCode = "200", description = "Items found.")
    @ApiResponse(responseCode = "400",
            description = "Title must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "Title is required."), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid page number.",
            content = {@Content(examples = @ExampleObject(value = "Page number must be greater than or equal to zero."), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid page size.",
            content = {@Content(examples = @ExampleObject(value = "Page size must be positive number."), mediaType = "text/html")})
    @GetMapping("/byItem")
    public ResponseEntity<ItemGetByTitleResponse> getItemsByItemTitle(
            @RequestParam @NotBlank(message = "Title is required.") String title,
            @RequestParam @Min(value = 0, message = "Page number must be greater than or equal to zero.") Integer pageNumber,
            @RequestParam @Min(value = 1, message = "Page size must be positive number.") Integer pageSize
    ) {
        ItemGetByTitleRequest itemRequest = ItemGetByTitleRequest
                .builder()
                .title(title)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .build();

        ItemGetByTitleResponse response = this.itemGetByTitleOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Gets item by having the specified id.",
            summary = "Gets item by id.")
    @ApiResponse(responseCode = "200", description = "Item found.")
    @ApiResponse(responseCode = "400",
            description = "Item does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item is archived.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @GetMapping("/{id}")
    public ResponseEntity<GetItemByIdResponse> getItemById(@PathVariable String id) {
        GetItemByIdRequest itemRequest = GetItemByIdRequest
                .builder()
                .id(id)
                .build();

        GetItemByIdResponse response = this.getItemByIdOperation.process(itemRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Gets list of items by multiple ids.",
            summary = "Gets list of items.")
    @ApiResponse(responseCode = "200", description = "Items found.")
    @PostMapping("/list")
    public ResponseEntity<GetListOfItemsResponse> getListOfItemsByIds(
            @RequestBody GetListOfItemsRequest getListOfItemsRequest
    ) {
        GetListOfItemsResponse response = this.getListOfItemsOperation.process(getListOfItemsRequest);

        return ResponseEntity.ok(response);
    }

    @Operation(description = "Creates item with title, description and vendorId.",
            summary = "Creates item.")
    @ApiResponse(responseCode = "201", description = "Item created.")
    @ApiResponse(responseCode = "400",
            description = "Empty title parameter.",
            content = {@Content(examples = @ExampleObject(value = "The title must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Empty description parameter.",
            content = {@Content(examples = @ExampleObject(value = "The description must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Empty vendorId parameter.",
            content = {@Content(examples = @ExampleObject(value = "The vendorId must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Existing item creation.",
            content = {@Content(examples = @ExampleObject(value = "Item with that title is already existing!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "404",
            description = "Vendor with that id does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This vendor does not exist!"), mediaType = "text/html")})
    @PostMapping
    public ResponseEntity<ItemCreateResponse> createItem(
            @Valid @RequestBody ItemCreateRequest itemCreateRequest
    ) {
        ItemCreateResponse createdItem = this.itemCreateOperation.process(itemCreateRequest);

        return ResponseEntity.status(201).body(createdItem);
    }

    @Operation(description = "Updates item title/description.",
            summary = "Updates item title/description.")
    @ApiResponse(responseCode = "200", description = "Item updated.")
    @ApiResponse(responseCode = "400",
            description = "Empty item id parameter.",
            content = {@Content(examples = @ExampleObject(value = "Item id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @PutMapping
    public ResponseEntity<ItemUpdateResponse> updateItem(
            @Valid @RequestBody ItemUpdateRequest itemUpdateRequest
    ) {
        ItemUpdateResponse updatedItem = this.itemUpdateOperation.process(itemUpdateRequest);

        return ResponseEntity.ok(updatedItem);
    }

    @Operation(description = "Archives item by the specific item id.",
            summary = "Archives item.")
    @ApiResponse(responseCode = "200", description = "Item archived.")
    @ApiResponse(responseCode = "400",
            description = "Empty id parameter.",
            content = {@Content(examples = @ExampleObject(value = "The id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Trying to archive an already archived item.",
            content = {@Content(examples = @ExampleObject(value = "Item is already archived!"), mediaType = "text/html")})
    @PatchMapping("/archive")
    public ResponseEntity<ItemArchiveResponse> archiveItem(
            @Valid @RequestBody ItemArchiveRequest itemArchiveRequest
    ) {
        ItemArchiveResponse archivedItem = this.itemArchiveOperation.process(itemArchiveRequest);

        return ResponseEntity.ok(archivedItem);
    }

    @Operation(description = "Unarchive item by the specific item id.",
            summary = "Unarchive item.")
    @ApiResponse(responseCode = "200", description = "Item unarchived.")
    @ApiResponse(responseCode = "400",
            description = "Empty id parameter.",
            content = {@Content(examples = @ExampleObject(value = "The id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Not existing item.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Trying to unarchive an already unarchived item.",
            content = {@Content(examples = @ExampleObject(value = "Item is already unarchived!"), mediaType = "text/html")})
    @PatchMapping("/unarchive")
    public ResponseEntity<ItemUnarchiveResponse> unarchiveItem(
            @Valid @RequestBody ItemUnarchiveRequest itemUnarchiveRequest
    ) {
        ItemUnarchiveResponse unarchivedItem = this.itemUnarchiveOperation.process(itemUnarchiveRequest);

        return ResponseEntity.ok(unarchivedItem);
    }

    @Operation(description = "Adds multimedia to the specific item.",
            summary = "Adds multimedia to item.")
    @ApiResponse(responseCode = "200", description = "Multimedia added to item successfully.")
    @ApiResponse(responseCode = "400",
            description = "Empty id parameter.",
            content = {@Content(examples = @ExampleObject(value = "The id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Empty collection of ids.",
            content = {@Content(examples = @ExampleObject(value = "The multimediaId must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format in the collection.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID in the collection!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "404",
            description = "Multimedia does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No multimedia with that public ID!"), mediaType = "text/html")})
    @PatchMapping("/addMultimedia")
    public ResponseEntity<ItemAddMultimediaResponse> addMultimediaToItem(
            @Valid @RequestBody ItemAddMultimediaRequest itemAddMultimediaRequest
    ) {
        ItemAddMultimediaResponse item = this.itemAddMultimediaOperation.process(itemAddMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @Operation(description = "Removes multimedia from the specific item.",
            summary = "Removes multimedia from item.")
    @ApiResponse(responseCode = "200", description = "Multimedia removed from item successfully.")
    @ApiResponse(responseCode = "400",
            description = "Empty id parameter.",
            content = {@Content(examples = @ExampleObject(value = "The id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Empty collection of ids.",
            content = {@Content(examples = @ExampleObject(value = "The multimediaId must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format in the collection.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID in the collection!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "404",
            description = "Multimedia does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No multimedia with that public ID!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Multimedia doesn't belong to this item.",
            content = {@Content(examples = @ExampleObject(value = "Multimedia with ID: 'multimediaId' doesn't belong to this item!"), mediaType = "text/html")})
    @PatchMapping("/removeMultimedia")
    public ResponseEntity<ItemRemoveMultimediaResponse> removeMultimediaFromItem(
            @Valid @RequestBody ItemRemoveMultimediaRequest itemRemoveMultimediaRequest
    ) {
        ItemRemoveMultimediaResponse item = this.itemRemoveMultimediaOperation.process(itemRemoveMultimediaRequest);

        return ResponseEntity.ok(item);
    }

    @Operation(description = "Adds tag to the specific item.",
            summary = "Adds tag to item.")
    @ApiResponse(responseCode = "200", description = "Tag added to item successfully.")
    @ApiResponse(responseCode = "400",
            description = "Empty id parameter.",
            content = {@Content(examples = @ExampleObject(value = "The id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Empty collection of ids.",
            content = {@Content(examples = @ExampleObject(value = "The tagsId must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format in the collection.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID in the collection!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "404",
            description = "Tag does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This tag does not exist!"), mediaType = "text/html")})
    @PatchMapping("/addTag")
    public ResponseEntity<ItemAddTagResponse> addTagToItem(
            @Valid @RequestBody ItemAddTagRequest itemAddTagRequest
    ) {
        ItemAddTagResponse item = this.itemAddTagOperation.process(itemAddTagRequest);

        return ResponseEntity.ok(item);
    }

    @Operation(description = "Removes tag from the specific item.",
            summary = "Removes tag from item.")
    @ApiResponse(responseCode = "200", description = "Tag removed from item successfully.")
    @ApiResponse(responseCode = "400",
            description = "Empty id parameter.",
            content = {@Content(examples = @ExampleObject(value = "The id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Empty collection of ids.",
            content = {@Content(examples = @ExampleObject(value = "The tagsId must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid UUID format in the collection.",
            content = {@Content(examples = @ExampleObject(value = "Invalid UUID in the collection!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Item does not exist.",
            content = {@Content(examples = @ExampleObject(value = "No such item!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "404",
            description = "Tag does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This tag does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Tag doesn't belong to this item.",
            content = {@Content(examples = @ExampleObject(value = "Tag with ID: 'tagId' doesn't belong to this item!"), mediaType = "text/html")})
    @PatchMapping("/removeTag")
    public ResponseEntity<ItemRemoveTagResponse> removeTagFromItem(
            @Valid @RequestBody ItemRemoveTagRequest itemRemoveTagRequest
    ) {
        ItemRemoveTagResponse item = this.itemRemoveTagOperation.process(itemRemoveTagRequest);

        return ResponseEntity.ok(item);
    }
}
