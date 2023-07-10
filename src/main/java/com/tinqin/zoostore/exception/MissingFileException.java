package com.tinqin.zoostore.exception;

public class MissingFileException extends RuntimeException {

    private static final String MESSAGE = "Please, select a file!";

    public MissingFileException() {
        super(MESSAGE);
    }
}
