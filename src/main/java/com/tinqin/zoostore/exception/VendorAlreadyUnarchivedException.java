package com.tinqin.zoostore.exception;

public class VendorAlreadyUnarchivedException extends RuntimeException {

    private static final String MESSAGE = "This vendor is already unarchived!";

    public VendorAlreadyUnarchivedException() {
        super(MESSAGE);
    }
}
