package com.kruczek.theravensystem.rss.download;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RssNews {

    @Value("${rss.news.old:0}")
    private long LIMIT_OF_OLD_NEWS;

    @Value("${rss.news.valid.in.millis:1800000}")
    private long VALID_NEWS_IN_MILLIS;

    public List<SyndEntry> getNewsFrom(List<String> rssUrls) {
        List<SyndEntry> result = new ArrayList<>();

        Instant dateAfterNewsCollected = Instant.now().minus(VALID_NEWS_IN_MILLIS, ChronoUnit.MILLIS);

        for (String rssUrl : rssUrls) {
            List<SyndEntry> contentOfFeed = getContentOfFeed(rssUrl);
            List<SyndEntry> filteredContent = contentOfFeed.stream()
                    .filter(content -> hasContentDateAfter(content, dateAfterNewsCollected))
                    .collect(Collectors.toList());

            if (filteredContent.isEmpty() && LIMIT_OF_OLD_NEWS > 0) {
                filteredContent = contentOfFeed.stream().limit(LIMIT_OF_OLD_NEWS).collect(Collectors.toList());
            }

            result.addAll(filteredContent);
        }

        return result;
    }

    private List<SyndEntry> getContentOfFeed(String rssUrl) {
        try {
            return new SyndFeedInput().build(new XmlReader(new URL(rssUrl))).getEntries();
        } catch (FeedException | IOException e) {
            log.warn("Error while parsing rss entities", e);
        }

        return Collections.emptyList();
    }

    private boolean hasContentDateAfter(SyndEntry content, Instant dateAfterNewsCollected) {
        Date contentDate = content.getUpdatedDate();

        if (contentDate == null) {
            contentDate = content.getPublishedDate();
        }

        return contentDate.toInstant().isAfter(dateAfterNewsCollected);
    }
}
