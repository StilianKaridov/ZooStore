package com.tinqin.zoostore.core.exception.item;

public class ItemAlreadyUnarchivedException extends RuntimeException {

    private static final String MESSAGE = "Item is already unarchived!";

    public ItemAlreadyUnarchivedException() {
        super(MESSAGE);
    }
}
