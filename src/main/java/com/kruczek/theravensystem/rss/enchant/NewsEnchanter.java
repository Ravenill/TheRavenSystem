package com.kruczek.theravensystem.rss.enchant;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.rometools.rome.feed.synd.SyndEntry;

import java.util.List;

public interface NewsEnchanter {
    List<RssNewsView> enchant(List<SyndEntry> rssNewsViews);

    String getContainsUrl();

    default RssNewsView createBasicRssNewsViewFrom(SyndEntry syndEntry) {
        return RssNewsView.builder()
                .title(syndEntry.getTitle())
                .desc(syndEntry.getDescription().getValue())
                .url(syndEntry.getUri())
                .build();
    }
}
