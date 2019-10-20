package com.kruczek.theravensystem;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.kruczek.theravensystem.rss.RssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RavenSystemService {

    private RssService rssService;

    @Autowired
    public RavenSystemService(RssService rssService) {
        this.rssService = rssService;
    }

    public void fetchAndSendRssFeed() {
        List<RssNewsView> feed = rssService.getDailyNews();
    }
}
