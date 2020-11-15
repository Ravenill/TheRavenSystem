package com.kruczek.theravensystem.rss.download;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.kruczek.theravensystem.rss.enchant.NewsEnchanter;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class RssNewsDownloader {

    @Value("${rss.news.old:0}")
    private long limitOfOldNews;

    @Value("${rss.news.valid.in.millis:1800000}")
    private long validNewsInMillis;

    private final NewsEnchanter defaultEnchanter;

    private final Map<String, NewsEnchanter> newsEnchanters;

    @Autowired
    public RssNewsDownloader(List<NewsEnchanter> newsEnchanters) {
        Map<String, NewsEnchanter> mapOfEnchanters = new HashMap<>();
        newsEnchanters.forEach(newsEnchanter -> mapOfEnchanters.put(newsEnchanter.getDefineUrl(), newsEnchanter));
        this.defaultEnchanter = mapOfEnchanters.get("default");
        this.newsEnchanters = mapOfEnchanters;
    }

    public List<RssNewsView> getNewsFrom(List<String> rssUrls) {
        List<RssNewsView> result = new ArrayList<>();

        Instant maximumDateOfValidNews = Instant.now().minus(validNewsInMillis, ChronoUnit.MILLIS);

        for (String rssUrl : rssUrls) {
            List<SyndEntry> contentOfFeed = getContentOfFeed(rssUrl);
            List<SyndEntry> filteredContent = contentOfFeed.stream()
                    .filter(isContentGeneratedAfter(maximumDateOfValidNews))
                    .collect(Collectors.toList());

            boolean shouldCollectOldData = limitOfOldNews > 0 && filteredContent.isEmpty();
            if (shouldCollectOldData) {
                filteredContent = contentOfFeed.stream().limit(limitOfOldNews).collect(Collectors.toList());
            }

            List<RssNewsView> enchantedAndFilteredNews = newsEnchanters.getOrDefault(rssUrl, defaultEnchanter)
                    .enchant(filteredContent);

            result.addAll(enchantedAndFilteredNews);
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

    private Predicate<SyndEntry> isContentGeneratedAfter(Instant maximumDateOfValidNews) {
        return content -> hasContentDateAfter(content, maximumDateOfValidNews);
    }

    private boolean hasContentDateAfter(SyndEntry content, Instant maximumDateOfValidNews) {
        Date contentDate = content.getUpdatedDate();

        if (contentDate == null) {
            contentDate = content.getPublishedDate();
        }

        return contentDate.toInstant().isAfter(maximumDateOfValidNews);
    }
}
