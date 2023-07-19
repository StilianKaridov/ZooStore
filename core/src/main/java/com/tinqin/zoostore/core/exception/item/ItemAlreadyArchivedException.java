package com.tinqin.zoostore.core.exception.item;

public class ItemAlreadyArchivedException extends RuntimeException {

    private static final String MESSAGE = "Item is already archived!";

    public ItemAlreadyArchivedException() {
        super(MESSAGE);
    }
}
