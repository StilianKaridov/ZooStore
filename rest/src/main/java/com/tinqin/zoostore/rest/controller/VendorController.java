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

    @PostMapping
    public ResponseEntity<VendorCreateResponse> createVendor(
            @Valid @RequestBody VendorCreateRequest vendorCreateRequest
    ) {
        VendorCreateResponse vendorResponse = this.vendorCreateOperation.process(vendorCreateRequest);

        return ResponseEntity.
                status(201).
                body(vendorResponse);
    }

    @PutMapping
    public ResponseEntity<VendorUpdateResponse> updateVendor(
            @Valid @RequestBody VendorUpdateRequest vendorUpdateRequest
    ) {
        VendorUpdateResponse updatedVendor = this.vendorUpdateOperation.process(vendorUpdateRequest);

        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/archive")
    public ResponseEntity<VendorArchiveResponse> archiveVendor(
            @Valid @RequestBody VendorArchiveRequest vendorArchiveRequest
    ) {
        VendorArchiveResponse archivedVendor = this.vendorArchiveOperation.process(vendorArchiveRequest);

        return ResponseEntity.ok(archivedVendor);
    }

    @PatchMapping("/unarchive")
    public ResponseEntity<VendorUnarchiveResponse> unarchiveVendor(
            @Valid @RequestBody VendorUnarchiveRequest vendorUnarchiveRequest
    ) {
        VendorUnarchiveResponse unarchivedVendor = this.vendorUnarchiveOperation.process(vendorUnarchiveRequest);

        return ResponseEntity.ok(unarchivedVendor);
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<VendorDeleteResponse> deleteVendor(
            @Valid @RequestBody VendorDeleteRequest vendorDeleteRequest
    ) {
        VendorDeleteResponse deletedVendor = this.vendorDeleteOperation.process(vendorDeleteRequest);

        return ResponseEntity.ok(deletedVendor);
    }
}
