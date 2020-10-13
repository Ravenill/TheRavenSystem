package com.kruczek.theravensystem.telegram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class TelegramMessageService {
    private final TelegramBotContext telegramBotContext;
    private final ChannelMapper channelMapper;

    @Autowired
    TelegramMessageService(TelegramBotContext telegramBotContext, ChannelMapper channelMapper) {
        this.telegramBotContext = telegramBotContext;
        this.channelMapper = channelMapper;
    }

    public void sendMessage(String message, ChannelName channelName) {
        try {
            telegramBotContext.sendMessage(message, channelMapper.getChannelId(channelName));
        } catch (TelegramApiException e) {
            log.error("Can't send message on Telegram: ", e);
            throw new CannotSendMessageException(e);
        }
    }
}
