package com.kruczek.theravensystem.rss.enchant;

import java.util.List;
import java.util.stream.Collectors;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.rometools.rome.feed.synd.SyndEntry;

public abstract class NewsEnchanter {

    protected abstract RssNewsView buildRssNewsEntry(SyndEntry rssNewsViews);

    public abstract String getDefineUrl();

    public List<RssNewsView> enchant(List<SyndEntry> syndEntries) {
        return syndEntries.stream()
                .map(this::buildRssNewsEntry)
                .collect(Collectors.toList());
    }

    protected RssNewsView buildDefaultEntry(SyndEntry syndEntry) {
        return RssNewsView.builder()
                .title(syndEntry.getTitle())
                .desc(syndEntry.getDescription().getValue())
                .url(syndEntry.getUri())
                .build();
    }
}
