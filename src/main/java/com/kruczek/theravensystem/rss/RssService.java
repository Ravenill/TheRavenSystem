package com.kruczek.theravensystem.rss;

import com.kruczek.theravensystem.rss.helpers.SyndEntryConverter;
import com.kruczek.theravensystem.rss.enchant.ViewEnchanter;
import com.kruczek.theravensystem.rss.download.RssNews;
import com.kruczek.theravensystem.rss.source.DatabaseRssChannelSource;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RssService {

    private RssNews rssNews;
    private ViewEnchanter viewEnchanter;
    private SyndEntryConverter syndEntryConverter;
    private DatabaseRssChannelSource rssChannelSource;

    @Autowired
    public RssService(RssNews rssNews, ViewEnchanter viewEnchanter, SyndEntryConverter syndEntryConverter, DatabaseRssChannelSource rssChannelSource) {
        this.rssNews = rssNews;
        this.viewEnchanter = viewEnchanter;
        this.syndEntryConverter = syndEntryConverter;
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
        List<SyndEntry> rssDailyNews = rssNews.getNewsFrom(urls);
        List<RssNewsView> rssNewsViews = syndEntryConverter.toRssNewsView(rssDailyNews);
        return viewEnchanter.enchant(rssNewsViews);
    }
}
