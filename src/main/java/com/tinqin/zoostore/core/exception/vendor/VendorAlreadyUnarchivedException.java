package com.tinqin.zoostore.api.exception.vendor;

public class VendorAlreadyUnarchivedException extends RuntimeException {

    private static final String MESSAGE = "This vendor is already unarchived!";

    public VendorAlreadyUnarchivedException() {
        super(MESSAGE);
    }
}
