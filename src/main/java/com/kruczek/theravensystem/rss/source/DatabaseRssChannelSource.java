package com.kruczek.theravensystem.rss.source;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kruczek.theravensystem.entity.tables.RssChannels;
import com.kruczek.theravensystem.rss.RssCategory;

@Repository
public class DatabaseRssChannelSource implements RssChannelSource {

    private static final RssChannels RSS_CHANNELS = RssChannels.RSS_CHANNELS;

    private final DSLContext create;

    @Autowired
    DatabaseRssChannelSource(DSLContext create) {
        this.create = create;
    }

    @Override
    public void addChannel(String url) {
        addChannel(url, RssCategory.OTHER);
    }

    @Override
    public void addChannel(String url, RssCategory category) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteChannel(String url) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<RssCategory, List<String>> getAllChannels() {
        final Map<RssCategory, List<String>> result = new EnumMap<>(RssCategory.class);

        create.select(RSS_CHANNELS.RSS_TYPE, RSS_CHANNELS.URL)
                .from(RSS_CHANNELS)
                .fetch()
                .intoGroups(RSS_CHANNELS.RSS_TYPE, RSS_CHANNELS.URL)
                .forEach((key, val) -> result.put(RssCategory.valueOf(key.name()), val));

        return result;
    }

    @Override
    public List<String> getChannels(RssCategory category) {
        throw new UnsupportedOperationException();
    }
}
