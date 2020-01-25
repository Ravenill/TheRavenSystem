package com.kruczek.theravensystem.rss.source;

import com.kruczek.theravensystem.config.CustomSessionManager;
import com.kruczek.theravensystem.rss.RssCategory;
import com.kruczek.theravensystem.rss.entity.RssChannel;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional
public class DatabaseRssChannelSource implements RssChannelSource {

    private CustomSessionManager sessionManager;

    @Autowired
    public DatabaseRssChannelSource(CustomSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void addChannel(String url) {
        addChannel(url, RssCategory.OTHER);
    }

    @Override
    public void addChannel(String url, RssCategory category) {
        // TODO: Zrób to zjebie
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteChannel(String url) {
        // TODO: Zrób to zjebie
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<RssCategory, List<String>> getAllChannels() {
        Session session = sessionManager.getSession();
        List<RssChannel> rssChannels = session.createCriteria(RssChannel.class).list();

        Map<RssCategory, List<String>> result = new HashMap<>();

        for (RssChannel rssChannel : rssChannels) {
            RssCategory rssType = rssChannel.getRssType();
            String channelUrl = rssChannel.getUrl();

            List<String> actualListOnCategory = result.putIfAbsent(rssType, Arrays.asList(channelUrl));
            if (actualListOnCategory != null) {
                actualListOnCategory.add(channelUrl);
                result.put(rssType, actualListOnCategory);
            }
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getChannels(RssCategory category) {
        Session session = sessionManager.getSession();
        List<RssChannel> rssChannels = session.createCriteria(RssChannel.class).add(Restrictions.eq("rss_type", category)).list();

        return rssChannels.stream().map(RssChannel::getUrl).collect(Collectors.toList());
    }
}
