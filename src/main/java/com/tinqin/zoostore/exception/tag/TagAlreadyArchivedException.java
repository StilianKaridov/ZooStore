package com.tinqin.zoostore.exception.tag;

public class TagAlreadyArchivedException extends RuntimeException {

    private static final String MESSAGE = "Tag is already archived!";

    public TagAlreadyArchivedException() {
        super(MESSAGE);
    }
}
