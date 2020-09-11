package com.kruczek.theravensystem.exception;

public class CannotSendMessageException extends RuntimeException {

    private static final String MESSAGE = "Cannot send message :c";

    public CannotSendMessageException() {
        super(MESSAGE);
    }

    public CannotSendMessageException(Exception exception) {
        super(exception.getMessage(), exception.getCause());
    }
}
