package com.kruczek.theravensystem.exception;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
class ExceptionMessageFormatter {

    private static final String SEPARATOR = "\n_______________________________________\n";

    List<String> formatException(Throwable e) {
        final StringBuilder exceptionMessageBuilder = new StringBuilder(e.toString());
        exceptionMessageBuilder.append("\n").append(e.getLocalizedMessage());

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

        return splitMessageByBytesSize(exceptionMessageBuilder.toString(), 4096);
    }

    private List<String> splitMessageByBytesSize(String messageToSplit, int chunkSize) {
        final boolean isNotNeedToSplit = messageToSplit.getBytes().length < chunkSize;
        if (isNotNeedToSplit) {
            return Collections.singletonList(messageToSplit);
        }

        byte[] buffer = new byte[chunkSize];
        int end = buffer.length;
        int start = 0;
        long remaining = messageToSplit.getBytes().length;

        final List<String> result = new ArrayList<>();
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(messageToSplit.getBytes());

        while ((inputStream.read(buffer, start, end)) != -1) {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(buffer, start, end);
            result.add(outputStream.toString(StandardCharsets.UTF_8));
            remaining = remaining - end;

            if (remaining <= end) {
                end = (int) remaining;
            }
        }

        return result;

    }
}
