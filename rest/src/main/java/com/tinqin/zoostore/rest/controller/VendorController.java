package com.tinqin.zoostore.rest.controller;

import com.tinqin.zoostore.api.operations.vendor.archive.VendorArchiveOperation;
import com.tinqin.zoostore.api.operations.vendor.archive.VendorArchiveRequest;
import com.tinqin.zoostore.api.operations.vendor.archive.VendorArchiveResponse;
import com.tinqin.zoostore.api.operations.vendor.create.VendorCreateOperation;
import com.tinqin.zoostore.api.operations.vendor.create.VendorCreateRequest;
import com.tinqin.zoostore.api.operations.vendor.create.VendorCreateResponse;
import com.tinqin.zoostore.api.operations.vendor.delete.VendorDeleteOperation;
import com.tinqin.zoostore.api.operations.vendor.delete.VendorDeleteRequest;
import com.tinqin.zoostore.api.operations.vendor.delete.VendorDeleteResponse;
import com.tinqin.zoostore.api.operations.vendor.unarchive.VendorUnarchiveOperation;
import com.tinqin.zoostore.api.operations.vendor.unarchive.VendorUnarchiveRequest;
import com.tinqin.zoostore.api.operations.vendor.unarchive.VendorUnarchiveResponse;
import com.tinqin.zoostore.api.operations.vendor.update.VendorUpdateOperation;
import com.tinqin.zoostore.api.operations.vendor.update.VendorUpdateRequest;
import com.tinqin.zoostore.api.operations.vendor.update.VendorUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zoostore/vendors")
public class VendorController {

    private final VendorCreateOperation vendorCreateOperation;
    private final VendorArchiveOperation vendorArchiveOperation;
    private final VendorUnarchiveOperation vendorUnarchiveOperation;
    private final VendorDeleteOperation vendorDeleteOperation;
    private final VendorUpdateOperation vendorUpdateOperation;

    @Autowired
    public VendorController(
            VendorCreateOperation vendorCreateOperation,
            VendorArchiveOperation vendorArchiveOperation,
            VendorUnarchiveOperation vendorUnarchiveOperation,
            VendorDeleteOperation vendorDeleteOperation,
            VendorUpdateOperation vendorUpdateOperation) {
        this.vendorCreateOperation = vendorCreateOperation;
        this.vendorArchiveOperation = vendorArchiveOperation;
        this.vendorUnarchiveOperation = vendorUnarchiveOperation;
        this.vendorDeleteOperation = vendorDeleteOperation;
        this.vendorUpdateOperation = vendorUpdateOperation;
    }

    @Operation(description = "Creates vendor with name and phoneNumber.",
            summary = "Creates vendor.")
    @ApiResponse(responseCode = "201", description = "Successfully created vendor.")
    @ApiResponse(responseCode = "400",
            description = "Vendor exists.",
            content = {@Content(examples = @ExampleObject(value = "Vendor already existing!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Name must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The name must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid phone number format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid phone number format!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Phone number must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The phone number must not be empty!"), mediaType = "text/html")})
    @PostMapping
    public ResponseEntity<VendorCreateResponse> createVendor(
            @Valid @RequestBody VendorCreateRequest vendorCreateRequest
    ) {
        VendorCreateResponse vendorResponse = this.vendorCreateOperation.process(vendorCreateRequest);

        return ResponseEntity.
                status(201).
                body(vendorResponse);
    }

    @Operation(description = "Updates vendor name or phoneNumber.",
            summary = "Updates vendor name or phoneNumber.")
    @ApiResponse(responseCode = "200", description = "Successfully updated vendor.")
    @ApiResponse(responseCode = "400",
            description = "Vendor does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This vendor does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Vendor new name or new phoneNumber exists.",
            content = {@Content(examples = @ExampleObject(value = "Vendor already existing!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Vendor id must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "Vendor id must not be empty!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Invalid new phone number format.",
            content = {@Content(examples = @ExampleObject(value = "Invalid phone number format!"), mediaType = "text/html")})
    @PutMapping
    public ResponseEntity<VendorUpdateResponse> updateVendor(
            @Valid @RequestBody VendorUpdateRequest vendorUpdateRequest
    ) {
        VendorUpdateResponse updatedVendor = this.vendorUpdateOperation.process(vendorUpdateRequest);

        return ResponseEntity.ok(updatedVendor);
    }

    @Operation(description = "Archives vendor by name.",
            summary = "Archives vendor.")
    @ApiResponse(responseCode = "200", description = "Successfully archived vendor.")
    @ApiResponse(responseCode = "400",
            description = "Vendor does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This vendor does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Vendor already archived.",
            content = {@Content(examples = @ExampleObject(value = "This vendor is already archived!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Name must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The name must not be empty!"), mediaType = "text/html")})
    @PatchMapping("/archive")
    public ResponseEntity<VendorArchiveResponse> archiveVendor(
            @Valid @RequestBody VendorArchiveRequest vendorArchiveRequest
    ) {
        VendorArchiveResponse archivedVendor = this.vendorArchiveOperation.process(vendorArchiveRequest);

        return ResponseEntity.ok(archivedVendor);
    }

    @Operation(description = "Unarchive vendor by name.",
            summary = "Unarchive vendor.")
    @ApiResponse(responseCode = "200", description = "Successfully unarchived vendor.")
    @ApiResponse(responseCode = "400",
            description = "Vendor does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This vendor does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Vendor already unarchived.",
            content = {@Content(examples = @ExampleObject(value = "This vendor is already unarchived!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Name must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The name must not be empty!"), mediaType = "text/html")})
    @PatchMapping("/unarchive")
    public ResponseEntity<VendorUnarchiveResponse> unarchiveVendor(
            @Valid @RequestBody VendorUnarchiveRequest vendorUnarchiveRequest
    ) {
        VendorUnarchiveResponse unarchivedVendor = this.vendorUnarchiveOperation.process(vendorUnarchiveRequest);

        return ResponseEntity.ok(unarchivedVendor);
    }

    @Operation(description = "Deletes vendor by name.",
            summary = "Deletes vendor.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted vendor.")
    @ApiResponse(responseCode = "400",
            description = "Vendor does not exist.",
            content = {@Content(examples = @ExampleObject(value = "This vendor does not exist!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Vendor archived, cannot delete.",
            content = {@Content(examples = @ExampleObject(value = "This vendor is archived, you cannot delete him!"), mediaType = "text/html")})
    @ApiResponse(responseCode = "400",
            description = "Name must not be blank.",
            content = {@Content(examples = @ExampleObject(value = "The name must not be empty!"), mediaType = "text/html")})
    @Transactional
    @DeleteMapping
    public ResponseEntity<VendorDeleteResponse> deleteVendor(
            @Valid @RequestBody VendorDeleteRequest vendorDeleteRequest
    ) {
        VendorDeleteResponse deletedVendor = this.vendorDeleteOperation.process(vendorDeleteRequest);

        return ResponseEntity.ok(deletedVendor);
    }
}
