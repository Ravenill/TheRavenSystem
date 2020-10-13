package com.kruczek.theravensystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kruczek.theravensystem.rss.RssService;
import com.kruczek.theravensystem.telegram.TelegramMessageService;

@Service
public class RavenSystemCore {

    private final RssService rssService;
    private final TelegramMessageService telegramMessageService;

    @Autowired
    public RavenSystemCore(RssService rssService, TelegramMessageService telegramMessageService) {
        this.rssService = rssService;
        this.telegramMessageService = telegramMessageService;
    }

    public void fetchAndSendRssFeed() {

    }
}
