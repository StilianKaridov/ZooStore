package com.tinqin.zoostore.api.exception.tag;

public class OccupiedTagTitleException extends RuntimeException {

    private static final String OCCUPIED_TAG_TITLE_EXCEPTION = "This title is occupied. Please, choose a new title!";

    public OccupiedTagTitleException() {
        super(OCCUPIED_TAG_TITLE_EXCEPTION);
    }
}
