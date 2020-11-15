package com.kruczek.theravensystem.rss.enchant;

import org.springframework.stereotype.Component;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.rometools.rome.feed.synd.SyndEntry;

@Component
class DefaultEnchanter extends NewsEnchanter {

    @Override
    public String getDefineUrl() {
        return "default";
    }

    @Override
    protected RssNewsView buildRssNewsEntry(SyndEntry rssNewsViews) {
        return super.buildDefaultEntry(rssNewsViews);
    }
}
