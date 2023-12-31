package com.tinqin.zoostore.core.exception.item;

public class NoSuchItemException extends RuntimeException {

    private static final String MESSAGE = "No such item!";

    public NoSuchItemException() {
        super(MESSAGE);
    }
}
