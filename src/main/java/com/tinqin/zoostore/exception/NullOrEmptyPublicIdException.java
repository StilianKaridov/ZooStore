package com.tinqin.zoostore.exception;

public class NullOrEmptyPublicIdException extends RuntimeException {

    private static final String MESSAGE = "Please, enter a Public ID!";

    public NullOrEmptyPublicIdException() {
        super(MESSAGE);
    }
}
