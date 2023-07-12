package com.tinqin.zoostore.exception.multimedia;

public class UnsupportedFileTypeException extends RuntimeException{

    private static final String MESSAGE = "Unsupported file type! You can upload only images and videos!";

    public UnsupportedFileTypeException() {
        super(MESSAGE);
    }
}
