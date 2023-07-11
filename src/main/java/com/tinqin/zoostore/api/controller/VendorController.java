package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.VendorCreateRequest;
import com.tinqin.zoostore.api.request.VendorUpdateNameRequest;
import com.tinqin.zoostore.api.request.VendorUpdatePhoneRequest;
import com.tinqin.zoostore.api.response.VendorCreateResponse;
import com.tinqin.zoostore.api.response.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.exception.NoSuchVendorException;
import com.tinqin.zoostore.exception.NullOrEmptyStringException;
import com.tinqin.zoostore.exception.TagAlreadyArchivedException;
import com.tinqin.zoostore.exception.TagAlreadyUnarchivedException;
import com.tinqin.zoostore.exception.VendorAlreadyArchivedException;
import com.tinqin.zoostore.exception.VendorAlreadyExistingException;
import com.tinqin.zoostore.exception.VendorAlreadyUnarchivedException;
import com.tinqin.zoostore.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {

    private static final String SUCCESSFULLY_CREATED_VENDOR_MESSAGE = "Successfully created Vendor with name: %s and phoneNumber: %s";

    private static final String SUCCESSFULLY_ARCHIVED_VENDOR_RESP_MESSAGE = "Successfully archived vendor with name %s";
    private static final String SUCCESSFULLY_UNARCHIVED_VENDOR_RESP_MESSAGE = "Successfully unarchived vendor with name %s";

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/createVendor")
    public ResponseEntity<String> createVendor(@RequestBody VendorCreateRequest vendorCreateRequest) {
        String name = vendorCreateRequest.getName();
        String phoneNumber = vendorCreateRequest.getPhoneNumber();

        if (checkIfStringIsNullOrEmpty(name) || checkIfStringIsNullOrEmpty(phoneNumber)) {
            throw new NullOrEmptyStringException();
        }

        VendorCreateResponse vendor = this.vendorService.createVendor(vendorCreateRequest);

        return ResponseEntity.
                status(201).
                body(
                        String.format(
                                SUCCESSFULLY_CREATED_VENDOR_MESSAGE,
                                vendor.getName(),
                                vendor.getPhoneNumber()
                        )
                );
    }

    @PatchMapping("/updateVendorName")
    public ResponseEntity<String> updateVendorName(
            @RequestBody VendorUpdateNameRequest vendorUpdateRequest
    ) {
        String vendorOldName = vendorUpdateRequest.getOldName();
        String vendorNewName = vendorUpdateRequest.getNewName();

        if (checkIfStringIsNullOrEmpty(vendorOldName) ||
                checkIfStringIsNullOrEmpty(vendorNewName)) {
            throw new NullOrEmptyStringException();
        }

        StringBuilder response = new StringBuilder();

        VendorUpdateNameResponse vendorWithUpdatedName = this.vendorService.
                updateVendorName(vendorOldName, vendorNewName);

        response.append(String.format(
                        "Successfully updated vendor's name from %s to %s.",
                        vendorWithUpdatedName.getOldName(),
                        vendorWithUpdatedName.getNewName()
                )
        );

        return ResponseEntity.
                status(200).
                body(response.toString());
    }

    @PatchMapping("/updateVendorPhone")
    public ResponseEntity<String> updateVendorPhone(
            @RequestBody VendorUpdatePhoneRequest vendorUpdatePhoneRequest
    ) {
        String vendorOldPhone = vendorUpdatePhoneRequest.getOldPhone();
        String vendorNewPhone = vendorUpdatePhoneRequest.getNewPhone();

        if (checkIfStringIsNullOrEmpty(vendorOldPhone) ||
                checkIfStringIsNullOrEmpty(vendorNewPhone)) {
            throw new NullOrEmptyStringException();
        }

        StringBuilder response = new StringBuilder();

        VendorUpdatePhoneResponse vendorWithUpdatedPhone = this.vendorService.
                updateVendorPhone(vendorOldPhone, vendorNewPhone);

        response.append(String.format(
                        "Successfully updated vendor's phone from %s to %s.",
                        vendorWithUpdatedPhone.getOldPhone(),
                        vendorWithUpdatedPhone.getNewPhone()
                )
        );

        return ResponseEntity.
                status(200).
                body(response.toString());
    }

    @PatchMapping("/archiveVendor/{vendorName}")
    public ResponseEntity<String> archiveTag(@PathVariable String vendorName) {
        if (!this.vendorService.archiveVendor(vendorName)) {
            throw new VendorAlreadyArchivedException();
        }

        return ResponseEntity.ok(
                String.format(SUCCESSFULLY_ARCHIVED_VENDOR_RESP_MESSAGE, vendorName)
        );
    }

    @PatchMapping("/unarchiveVendor/{vendorName}")
    public ResponseEntity<String> unarchiveTag(@PathVariable String vendorName) {
        if (!this.vendorService.unarchiveVendor(vendorName)) {
            throw new VendorAlreadyUnarchivedException();
        }

        return ResponseEntity.ok(
                String.format(SUCCESSFULLY_UNARCHIVED_VENDOR_RESP_MESSAGE, vendorName)
        );
    }


    private boolean checkIfStringIsNullOrEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    @ExceptionHandler(value = NullOrEmptyStringException.class)
    public ResponseEntity<String> handleNullOrEmptyNameOrPhoneNumberException(NullOrEmptyStringException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyExistingException.class)
    public ResponseEntity<String> handleExistingVendorException(VendorAlreadyExistingException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = NoSuchVendorException.class)
    public ResponseEntity<String> handleNotExistingVendorException(NoSuchVendorException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyArchivedException.class)
    public ResponseEntity<String> handleVendorAlreadyArchivedException(VendorAlreadyArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyUnarchivedException.class)
    public ResponseEntity<String> handleVendorAlreadyUnarchivedException(VendorAlreadyUnarchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
