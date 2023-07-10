package com.tinqin.zoostore.exception;

public class NullOrEmptyTitleException extends RuntimeException {

    private static final String MESSAGE = "The title must not be empty!";

    public NullOrEmptyTitleException() {
        super(MESSAGE);
    }
}
