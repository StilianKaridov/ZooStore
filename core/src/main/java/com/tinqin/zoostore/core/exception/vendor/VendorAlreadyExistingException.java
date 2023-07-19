package com.tinqin.zoostore.core.exception.vendor;

public class VendorAlreadyExistingException extends RuntimeException {

    private static final String MESSAGE = "Vendor already existing!";

    public VendorAlreadyExistingException() {
        super(MESSAGE);
    }
}
