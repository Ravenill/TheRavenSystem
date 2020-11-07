package com.kruczek.theravensystem.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ErrorHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.kruczek.theravensystem.telegram.TelegramBotContext;

import lombok.extern.log4j.Log4j;

@Log4j
@ControllerAdvice
public class CustomExceptionHandler implements ErrorHandler {

    private final TelegramBotContext telegramBotContext;
    private final ExceptionMessageFormatter exceptionMessageFormatter;

    @Value("${bot.telegram.channel.technical}")
    private Long technicalChannel;

    @Autowired
    CustomExceptionHandler(TelegramBotContext telegramBotContext, ExceptionMessageFormatter exceptionMessageFormatter) {
        this.telegramBotContext = telegramBotContext;
        this.exceptionMessageFormatter = exceptionMessageFormatter;
    }

    @Override
    @ExceptionHandler(value = Throwable.class)
    public void handleError(Throwable e) {
        log.error("Error catch!", e);
        exceptionMessageFormatter.formatException(e).forEach(this::tryToSentPartOfMessage);
    }

    private void tryToSentPartOfMessage(String partOfMessage) {
        try {
            telegramBotContext.sendMessage(partOfMessage, technicalChannel);
        } catch (TelegramApiException telegramApiException) {
            log.error(telegramApiException);
        }
    }
}
