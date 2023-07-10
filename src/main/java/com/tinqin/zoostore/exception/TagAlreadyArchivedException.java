package com.tinqin.zoostore.exception;

public class TagAlreadyArchivedException extends RuntimeException {

    private static final String MESSAGE = "Tag is already archived!";

    public TagAlreadyArchivedException() {
        super(MESSAGE);
    }
}
