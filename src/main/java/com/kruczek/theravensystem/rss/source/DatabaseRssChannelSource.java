package com.kruczek.theravensystem.rss.source;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kruczek.theravensystem.entity.enums.RssChannelsRssType;
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
        create.transaction(configuration -> DSL.using(configuration)
                .insertInto(RSS_CHANNELS)
                .columns(RSS_CHANNELS.RSS_TYPE, RSS_CHANNELS.URL)
                .values(RssChannelsRssType.valueOf(category.name()), url)
        );
    }

    @Override
    public void deleteChannel(String url) {
        create.transaction(configuration -> DSL.using(configuration)
                .deleteFrom(RSS_CHANNELS)
                .where(RSS_CHANNELS.URL.eq(url))
                .execute()
        );
    }

    @Override
    public Map<RssCategory, List<String>> getAllChannels() {
        final Map<RssCategory, List<String>> result = new EnumMap<>(RssCategory.class);

        create.transaction(configuration -> DSL.using(configuration)
                .select(RSS_CHANNELS.RSS_TYPE, RSS_CHANNELS.URL)
                .from(RSS_CHANNELS)
                .fetch()
                .intoGroups(RSS_CHANNELS.RSS_TYPE, RSS_CHANNELS.URL)
                .forEach((rssType, listOfUrls) -> result.put(RssCategory.valueOf(rssType.name()), listOfUrls))
        );

        return result;
    }

    @Override
    public List<String> getChannels(RssCategory category) {
        return create.transactionResult(configuration -> DSL.using(configuration)
                .select()
                .from(RSS_CHANNELS)
                .where(RSS_CHANNELS.RSS_TYPE.eq(RssChannelsRssType.valueOf(category.name())))
                .fetch(RSS_CHANNELS.URL)
        );
    }
}
