package com.tinqin.zoostore.exception;

public class TagAlreadyUnarchivedException extends RuntimeException {

    private static final String MESSAGE = "Tag is already unarchived!";

    public TagAlreadyUnarchivedException() {
        super(MESSAGE);
    }
}
