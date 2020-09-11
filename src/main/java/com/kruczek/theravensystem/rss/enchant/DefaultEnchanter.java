package com.kruczek.theravensystem.rss.enchant;

import com.kruczek.theravensystem.rss.RssNewsView;
import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultEnchanter implements NewsEnchanter {
    @Override
    public List<RssNewsView> enchant(List<SyndEntry> syndEntries) {
        List<RssNewsView> result = new ArrayList<>();
        syndEntries.forEach(syndEntry -> result.add(createBasicRssNewsViewFrom(syndEntry)));
        return result;
    }

    @Override
    public String getDefineUrl() {
        return "default";
    }
}
