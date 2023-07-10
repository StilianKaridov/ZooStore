package com.tinqin.zoostore.exception;

public class NullOrEmptyStringException extends RuntimeException {

    private static final String MESSAGE = "The field/s must not be empty!";

    public NullOrEmptyStringException() {
        super(MESSAGE);
    }
}
