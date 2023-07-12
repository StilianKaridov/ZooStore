package com.tinqin.zoostore.exception.item;

public class NoSuchItemException extends RuntimeException {

    private static final String MESSAGE = "No such item!";

    public NoSuchItemException() {
        super(MESSAGE);
    }
}
