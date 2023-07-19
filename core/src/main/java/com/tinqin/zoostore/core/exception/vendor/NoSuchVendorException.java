package com.tinqin.zoostore.core.exception.vendor;

public class NoSuchVendorException extends RuntimeException {

    private static final String MESSAGE = "This vendor does not exist!";

    public NoSuchVendorException() {
        super(MESSAGE);
    }
}
