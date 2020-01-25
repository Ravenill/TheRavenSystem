package com.kruczek.theravensystem.rss;

import com.kruczek.theravensystem.rss.download.RssNews;
import com.kruczek.theravensystem.rss.source.DatabaseRssChannelSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RssService {

    private RssNews rssNews;
    private DatabaseRssChannelSource rssChannelSource;

    @Autowired
    public RssService(RssNews rssNews, DatabaseRssChannelSource rssChannelSource) {
        this.rssNews = rssNews;
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

    public List<RssNewsView> getDailyNews() {
        Map<RssCategory, List<String>> rssChannelsMap = rssChannelSource.getAllChannels();

        List<String> urls = new ArrayList<>();
        rssChannelsMap.values().forEach(urls::addAll);
        return getDailyNewsInView(urls);
    }

    public List<RssNewsView> getDailyNews(RssCategory category) {
        List<String> urls = rssChannelSource.getChannels(category);
        return getDailyNewsInView(urls);
    }

    private List<RssNewsView> getDailyNewsInView(List<String> urls) {
        return rssNews.getNewsFrom(urls);
    }
}
