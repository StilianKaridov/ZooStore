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
import com.tinqin.zoostore.api.operations.vendor.update.name.VendorUpdateNameOperation;
import com.tinqin.zoostore.api.operations.vendor.update.name.VendorUpdateNameRequest;
import com.tinqin.zoostore.api.operations.vendor.update.name.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.operations.vendor.update.phone.VendorUpdatePhoneOperation;
import com.tinqin.zoostore.api.operations.vendor.update.phone.VendorUpdatePhoneRequest;
import com.tinqin.zoostore.api.operations.vendor.update.phone.VendorUpdatePhoneResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {

    private final VendorCreateOperation vendorCreateOperation;
    private final VendorUpdateNameOperation vendorUpdateNameOperation;
    private final VendorUpdatePhoneOperation vendorUpdatePhoneOperation;
    private final VendorArchiveOperation vendorArchiveOperation;
    private final VendorUnarchiveOperation vendorUnarchiveOperation;
    private final VendorDeleteOperation vendorDeleteOperation;

    @Autowired
    public VendorController(
            VendorCreateOperation vendorCreateOperation,
            VendorUpdateNameOperation vendorUpdateNameOperation,
            VendorUpdatePhoneOperation vendorUpdatePhoneOperation,
            VendorArchiveOperation vendorArchiveOperation,
            VendorUnarchiveOperation vendorUnarchiveOperation,
            VendorDeleteOperation vendorDeleteOperation
    ) {
        this.vendorCreateOperation = vendorCreateOperation;
        this.vendorUpdateNameOperation = vendorUpdateNameOperation;
        this.vendorUpdatePhoneOperation = vendorUpdatePhoneOperation;
        this.vendorArchiveOperation = vendorArchiveOperation;
        this.vendorUnarchiveOperation = vendorUnarchiveOperation;
        this.vendorDeleteOperation = vendorDeleteOperation;
    }

    @PostMapping("/createVendor")
    public ResponseEntity<VendorCreateResponse> createVendor(
            @Valid @RequestBody VendorCreateRequest vendorCreateRequest
    ) {
        VendorCreateResponse vendorResponse = this.vendorCreateOperation.process(vendorCreateRequest);

        return ResponseEntity.
                status(201).
                body(vendorResponse);
    }

    @PatchMapping("/updateVendorName")
    public ResponseEntity<VendorUpdateNameResponse> updateVendorName(
            @Valid @RequestBody VendorUpdateNameRequest vendorUpdateRequest
    ) {
        VendorUpdateNameResponse updatedVendor = this.vendorUpdateNameOperation.process(vendorUpdateRequest);

        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/updateVendorPhone")
    public ResponseEntity<VendorUpdatePhoneResponse> updateVendorPhone(
            @Valid @RequestBody VendorUpdatePhoneRequest vendorUpdatePhoneRequest
    ) {
        VendorUpdatePhoneResponse updatedVendor = this.vendorUpdatePhoneOperation.process(vendorUpdatePhoneRequest);

        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/archiveVendor/{vendorName}")
    public ResponseEntity<VendorArchiveResponse> archiveVendor(
            @Valid @RequestBody VendorArchiveRequest vendorArchiveRequest
    ) {
        VendorArchiveResponse archivedVendor = this.vendorArchiveOperation.process(vendorArchiveRequest);

        return ResponseEntity.ok(archivedVendor);
    }

    @PatchMapping("/unarchiveVendor/{vendorName}")
    public ResponseEntity<VendorUnarchiveResponse> unarchiveVendor(
            @Valid @RequestBody VendorUnarchiveRequest vendorUnarchiveRequest
    ) {
        VendorUnarchiveResponse unarchivedVendor = this.vendorUnarchiveOperation.process(vendorUnarchiveRequest);

        return ResponseEntity.ok(unarchivedVendor);
    }

    @Transactional
    @DeleteMapping("/deleteVendor/{vendorName}")
    public ResponseEntity<VendorDeleteResponse> deleteVendor(
            @Valid @RequestBody VendorDeleteRequest vendorDeleteRequest
    ) {
        VendorDeleteResponse deletedVendor = this.vendorDeleteOperation.process(vendorDeleteRequest);

        return ResponseEntity.ok(deletedVendor);
    }
}
