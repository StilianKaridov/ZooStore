package com.tinqin.zoostore.exception.tag;

public class TagDoesntBelongToItemException extends RuntimeException {

    private static final String MESSAGE = "Tag with ID: %s doesn't belong to this item!";

    public TagDoesntBelongToItemException(String id) {
        super(String.format(MESSAGE, id));
    }
}
