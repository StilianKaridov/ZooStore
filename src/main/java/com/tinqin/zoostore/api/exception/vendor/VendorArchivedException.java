package com.tinqin.zoostore.api.exception.vendor;

public class VendorArchivedException extends RuntimeException {

    private static final String MESSAGE = "This vendor is archived, you cannot delete him!";

    public VendorArchivedException() {
        super(MESSAGE);
    }
}