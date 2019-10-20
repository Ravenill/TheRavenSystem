package com.kruczek.theravensystem.rss.source;

import com.kruczek.theravensystem.rss.RssCategory;

import java.util.List;
import java.util.Map;

public interface RssChannelSource {

    void addChannel(String url);

    void addChannel(String url, RssCategory category);

    void deleteChannel(String url);

    Map<RssCategory, List<String>> getAllChannels();

    List<String> getChannels(RssCategory category);

}
