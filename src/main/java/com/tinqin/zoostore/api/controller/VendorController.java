package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.VendorCreateRequest;
import com.tinqin.zoostore.api.request.VendorUpdateNameRequest;
import com.tinqin.zoostore.api.request.VendorUpdatePhoneRequest;
import com.tinqin.zoostore.api.response.VendorArchiveResponse;
import com.tinqin.zoostore.api.response.VendorCreateResponse;
import com.tinqin.zoostore.api.response.VendorDeleteResponse;
import com.tinqin.zoostore.api.response.VendorUnarchiveResponse;
import com.tinqin.zoostore.api.response.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.exception.NoSuchVendorException;
import com.tinqin.zoostore.exception.NullOrEmptyStringException;
import com.tinqin.zoostore.exception.VendorAlreadyArchivedException;
import com.tinqin.zoostore.exception.VendorAlreadyExistingException;
import com.tinqin.zoostore.exception.VendorAlreadyUnarchivedException;
import com.tinqin.zoostore.exception.VendorArchivedException;
import com.tinqin.zoostore.service.VendorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorController {

    private final VendorService vendorService;

    @Autowired
    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/createVendor")
    public ResponseEntity<VendorCreateResponse> createVendor(
            @RequestBody VendorCreateRequest vendorCreateRequest
    ) {
        String name = vendorCreateRequest.getName();
        String phoneNumber = vendorCreateRequest.getPhoneNumber();

        if (checkIfStringIsNullOrEmpty(name) || checkIfStringIsNullOrEmpty(phoneNumber)) {
            throw new NullOrEmptyStringException();
        }

        VendorCreateResponse vendorResponse = this.vendorService.createVendor(vendorCreateRequest);

        return ResponseEntity.
                status(201).
                body(vendorResponse);
    }

    @PatchMapping("/updateVendorName")
    public ResponseEntity<VendorUpdateNameResponse> updateVendorName(
            @RequestBody VendorUpdateNameRequest vendorUpdateRequest
    ) {
        String vendorOldName = vendorUpdateRequest.getOldName();
        String vendorNewName = vendorUpdateRequest.getNewName();

        if (checkIfStringIsNullOrEmpty(vendorOldName) ||
                checkIfStringIsNullOrEmpty(vendorNewName)) {
            throw new NullOrEmptyStringException();
        }

        VendorUpdateNameResponse updatedVendor = this.vendorService.
                updateVendorName(vendorOldName, vendorNewName);

        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/updateVendorPhone")
    public ResponseEntity<VendorUpdatePhoneResponse> updateVendorPhone(
            @RequestBody VendorUpdatePhoneRequest vendorUpdatePhoneRequest
    ) {
        String vendorOldPhone = vendorUpdatePhoneRequest.getOldPhone();
        String vendorNewPhone = vendorUpdatePhoneRequest.getNewPhone();

        if (checkIfStringIsNullOrEmpty(vendorOldPhone) ||
                checkIfStringIsNullOrEmpty(vendorNewPhone)) {
            throw new NullOrEmptyStringException();
        }

        VendorUpdatePhoneResponse updatedVendor = this.vendorService.
                updateVendorPhone(vendorOldPhone, vendorNewPhone);

        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/archiveVendor/{vendorName}")
    public ResponseEntity<VendorArchiveResponse> archiveVendor(@PathVariable String vendorName) {
        VendorArchiveResponse archivedVendor = this.vendorService.archiveVendor(vendorName);

        return ResponseEntity.ok(archivedVendor);
    }

    @PatchMapping("/unarchiveVendor/{vendorName}")
    public ResponseEntity<VendorUnarchiveResponse> unarchiveVendor(@PathVariable String vendorName) {
        VendorUnarchiveResponse unarchivedVendor = this.vendorService.unarchiveVendor(vendorName);

        return ResponseEntity.ok(unarchivedVendor);
    }

    @Transactional
    @DeleteMapping("/deleteVendor/{vendorName}")
    public ResponseEntity<VendorDeleteResponse> deleteVendor(@PathVariable String vendorName) {
        VendorDeleteResponse deletedVendor = this.vendorService.deleteVendor(vendorName);

        return ResponseEntity.ok(deletedVendor);
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
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyArchivedException.class)
    public ResponseEntity<String> handleVendorAlreadyArchivedException(VendorAlreadyArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorAlreadyUnarchivedException.class)
    public ResponseEntity<String> handleVendorAlreadyUnarchivedException(VendorAlreadyUnarchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(value = VendorArchivedException.class)
    public ResponseEntity<String> handleOnVendorDeleteIsArchivedException(VendorArchivedException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
