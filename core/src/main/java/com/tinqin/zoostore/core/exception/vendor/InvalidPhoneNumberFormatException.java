package com.tinqin.zoostore.core.exception.vendor;

public class InvalidPhoneNumberFormatException extends RuntimeException {

    private static final String MESSAGE = "Invalid phone number format!";

    public InvalidPhoneNumberFormatException() {
        super(MESSAGE);
    }
}
