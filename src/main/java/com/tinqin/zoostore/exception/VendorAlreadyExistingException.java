package com.tinqin.zoostore.exception;

public class VendorAlreadyExistingException extends RuntimeException {

    private static final String MESSAGE = "Vendor already existing!";

    public VendorAlreadyExistingException() {
        super(MESSAGE);
    }
}
