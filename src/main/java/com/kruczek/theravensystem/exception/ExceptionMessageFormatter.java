package com.kruczek.theravensystem.exception;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class ExceptionMessageFormatter {

    private static final String SEPARATOR = "\n_______________________________________\n";

    public String formatException(Throwable e) {
        StringBuilder exceptionMessageBuilder = new StringBuilder(e.getLocalizedMessage());
        exceptionMessageBuilder.append("\n\n_________Cause_________\n").append(e.getCause())
                .append(SEPARATOR);

        exceptionMessageBuilder.append("\n\n_________StackTrace_________\n").append(Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n")))
                .append(SEPARATOR);

        exceptionMessageBuilder.append("\n\n_________Suppressed_________\n").append(Arrays.stream(e.getSuppressed())
                .map(Throwable::toString)
                .collect(Collectors.joining("\n")))
                .append(SEPARATOR);

        return exceptionMessageBuilder.toString();
    }
}
