package com.tinqin.zoostore.exception.vendor;

public class NoSuchVendorException extends RuntimeException {

    private static final String MESSAGE = "This vendor does not exist!";

    public NoSuchVendorException() {
        super(MESSAGE);
    }
}
