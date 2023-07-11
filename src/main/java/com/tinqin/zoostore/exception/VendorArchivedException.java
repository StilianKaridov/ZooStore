package com.tinqin.zoostore.exception;

public class VendorArchivedException extends RuntimeException {

    private static final String MESSAGE = "The vendor is archived, you cannot delete!";

    public VendorArchivedException() {
        super(MESSAGE);
    }
}
