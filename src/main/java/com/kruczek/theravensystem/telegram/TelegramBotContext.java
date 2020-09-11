package com.kruczek.theravensystem.telegram;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class TelegramBotContext extends TelegramLongPollingBot {

    @Value("${bot.telegram.name}")
    private String botName;

    @Value("${bot.telegram.token}")
    private String token;

    @PostConstruct
    private void init() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        //NOTHING
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendMessage(String messageText, long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(messageText);

        execute(message);
    }
}
