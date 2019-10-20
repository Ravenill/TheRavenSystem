package com.kruczek.theravensystem.rss.helpers;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SyndEntryConverter {

    public RssNewsView toRssNewsView(SyndEntry value) {
        return RssNewsView.builder()
                .title(value.getTitle())
                .desc(value.getDescription().getValue())
                .url(value.getUri())
                .build();
    }

    public List<RssNewsView> toRssNewsView(List<SyndEntry> values) {
        return values.stream()
                .map(this::toRssNewsView)
                .collect(Collectors.toList());
    }
}
