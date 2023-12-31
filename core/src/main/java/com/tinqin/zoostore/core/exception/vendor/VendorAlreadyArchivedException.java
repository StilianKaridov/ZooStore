package com.tinqin.zoostore.core.exception.vendor;

public class VendorAlreadyArchivedException extends RuntimeException {

    private static final String MESSAGE = "This vendor is already archived!";

    public VendorAlreadyArchivedException() {
        super(MESSAGE);
    }
}
