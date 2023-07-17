package com.tinqin.zoostore.api.exception.tag;

public class TagAlreadyUnarchivedException extends RuntimeException {

    private static final String MESSAGE = "Tag is already unarchived!";

    public TagAlreadyUnarchivedException() {
        super(MESSAGE);
    }
}
