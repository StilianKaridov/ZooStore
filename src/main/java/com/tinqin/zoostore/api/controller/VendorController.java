package com.tinqin.zoostore.api.controller;

import com.tinqin.zoostore.api.request.vendor.VendorCreateRequest;
import com.tinqin.zoostore.api.request.vendor.VendorUpdateNameRequest;
import com.tinqin.zoostore.api.request.vendor.VendorUpdatePhoneRequest;
import com.tinqin.zoostore.api.response.vendor.VendorArchiveResponse;
import com.tinqin.zoostore.api.response.vendor.VendorCreateResponse;
import com.tinqin.zoostore.api.response.vendor.VendorDeleteResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUnarchiveResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUpdateNameResponse;
import com.tinqin.zoostore.api.response.vendor.VendorUpdatePhoneResponse;
import com.tinqin.zoostore.exception.vendor.NoSuchVendorException;
import com.tinqin.zoostore.exception.vendor.VendorAlreadyArchivedException;
import com.tinqin.zoostore.exception.vendor.VendorAlreadyExistingException;
import com.tinqin.zoostore.exception.vendor.VendorAlreadyUnarchivedException;
import com.tinqin.zoostore.exception.vendor.VendorArchivedException;
import com.tinqin.zoostore.service.VendorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
            @Valid @RequestBody VendorCreateRequest vendorCreateRequest
    ) {
        VendorCreateResponse vendorResponse = this.vendorService.createVendor(vendorCreateRequest);

        return ResponseEntity.
                status(201).
                body(vendorResponse);
    }

    @PatchMapping("/updateVendorName")
    public ResponseEntity<VendorUpdateNameResponse> updateVendorName(
            @Valid @RequestBody VendorUpdateNameRequest vendorUpdateRequest
    ) {
        String vendorOldName = vendorUpdateRequest.getOldName();
        String vendorNewName = vendorUpdateRequest.getNewName();

        VendorUpdateNameResponse updatedVendor = this.vendorService.
                updateVendorName(vendorOldName, vendorNewName);

        return ResponseEntity.ok(updatedVendor);
    }

    @PatchMapping("/updateVendorPhone")
    public ResponseEntity<VendorUpdatePhoneResponse> updateVendorPhone(
            @Valid @RequestBody VendorUpdatePhoneRequest vendorUpdatePhoneRequest
    ) {
        String vendorOldPhone = vendorUpdatePhoneRequest.getOldPhone();
        String vendorNewPhone = vendorUpdatePhoneRequest.getNewPhone();

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNullOrEmptyFieldException(MethodArgumentNotValidException ex) {
        StringBuilder sb = new StringBuilder();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            sb.append(fieldError.getDefaultMessage()).append("\n");
        }

        return ResponseEntity.badRequest().body(sb.toString());
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
