package com.kruczek.theravensystem.rss;

import com.kruczek.theravensystem.rss.download.RssNewsDownloader;
import com.kruczek.theravensystem.rss.source.DatabaseRssChannelSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RssService {

    private final RssNewsDownloader rssNewsDownloader;
    private final DatabaseRssChannelSource rssChannelSource;

    @Autowired
    public RssService(RssNewsDownloader rssNewsDownloader, DatabaseRssChannelSource rssChannelSource) {
        this.rssNewsDownloader = rssNewsDownloader;
        this.rssChannelSource = rssChannelSource;
    }

    public void addSource(String url) {
        rssChannelSource.addChannel(url);
    }

    public void addSource(String url, RssCategory category) {
        rssChannelSource.addChannel(url, category);
    }

    public void deleteSource(String url) {
        rssChannelSource.deleteChannel(url);
    }

    public List<String> getListOfSources() {
        Map<RssCategory, List<String>> rssChannelUrlsMap = rssChannelSource.getAllChannels();

        List<String> urls = new ArrayList<>();
        rssChannelUrlsMap.values().forEach(urls::addAll);

        return urls;
    }

    public List<String> getListOfSources(RssCategory category) {
        return rssChannelSource.getChannels(category);
    }

    public List<RssNewsView> getDailyNews() {
        return getDailyNewsInView(getListOfSources());
    }

    public List<RssNewsView> getDailyNews(RssCategory category) {
        return getDailyNewsInView(getListOfSources(category));
    }

    private List<RssNewsView> getDailyNewsInView(List<String> urls) {
        return rssNewsDownloader.getNewsFrom(urls);
    }
}
