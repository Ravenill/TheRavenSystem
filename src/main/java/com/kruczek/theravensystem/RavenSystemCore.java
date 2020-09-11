package com.kruczek.theravensystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.kruczek.theravensystem.rss.RssService;
import com.kruczek.theravensystem.telegram.ChannelName;
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
        List<RssNewsView> feed = rssService.getDailyNews();
        feed.forEach(message -> telegramMessageService.sendMessage(message.toString(), ChannelName.RS_DAILY_FEED_CHANNEL));
    }
}
