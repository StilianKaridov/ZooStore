package com.tinqin.zoostore.api.exception.multimedia;

public class NoSuchMultimediaException extends RuntimeException{

    private static final String MESSAGE = "No multimedia with that public ID!";

    public NoSuchMultimediaException() {
        super(MESSAGE);
    }
}
