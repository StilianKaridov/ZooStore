package com.tinqin.zoostore.exception;

public class MultimediaDeletionException extends RuntimeException{

    private static final String MESSAGE = "Error occurred during image deletion.";

    public MultimediaDeletionException() {
        super(MESSAGE);
    }
}
