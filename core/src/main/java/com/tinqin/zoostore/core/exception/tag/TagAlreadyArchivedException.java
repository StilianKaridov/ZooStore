package com.tinqin.zoostore.core.exception.tag;

public class TagAlreadyArchivedException extends RuntimeException {

    private static final String MESSAGE = "Tag is already archived!";

    public TagAlreadyArchivedException() {
        super(MESSAGE);
    }
}
