package com.tinqin.zoostore.core.exception.item;

public class ItemAlreadyExistingException extends RuntimeException {

    private static final String MESSAGE = "Item with that title is already existing!";

    public ItemAlreadyExistingException() {
        super(MESSAGE);
    }
}
