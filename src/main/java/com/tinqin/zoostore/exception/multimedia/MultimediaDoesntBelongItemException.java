package com.tinqin.zoostore.exception.multimedia;

public class MultimediaDoesntBelongItemException extends RuntimeException {

    private static final String MESSAGE = "Multimedia with ID: %s doesn't belong to this item!";

    public MultimediaDoesntBelongItemException(String id) {
        super(String.format(MESSAGE, id));
    }
}
