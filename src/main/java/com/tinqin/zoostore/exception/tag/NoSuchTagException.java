package com.tinqin.zoostore.exception.tag;

public class NoSuchTagException extends RuntimeException {

    private static final String MESSAGE = "This tag does not exist!";

    public NoSuchTagException() {
        super(MESSAGE);
    }
}
