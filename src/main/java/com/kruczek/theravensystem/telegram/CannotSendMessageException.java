package com.kruczek.theravensystem.telegram;

class CannotSendMessageException extends RuntimeException {

    private static final String MESSAGE = "Cannot send message :c";

    CannotSendMessageException() {
        super(MESSAGE);
    }

    CannotSendMessageException(Exception exception) {
        super(exception.getMessage(), exception.getCause());
    }
}
