package com.tinqin.zoostore.exception.vendor;

public class VendorAlreadyExistingException extends RuntimeException {

    private static final String MESSAGE = "Vendor already existing!";

    public VendorAlreadyExistingException() {
        super(MESSAGE);
    }
}
