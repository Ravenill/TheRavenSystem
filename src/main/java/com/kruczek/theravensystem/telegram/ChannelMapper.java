package com.kruczek.theravensystem.telegram;

import java.util.EnumMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class ChannelMapper {

    @Value("${bot.telegram.channel.serverinfo}")
    private Long serverChannel;

    @Value("${bot.telegram.channel.technical}")
    private Long technicalChannel;

    @Value("${bot.telegram.channel.feed}")
    private Long dailyFeedChannel;

    private final Map<ChannelName, Long> channelMap = new EnumMap<>(ChannelName.class);

    @PostConstruct
    private void initMap() {
        channelMap.put(ChannelName.SERVER_INFO, serverChannel);
        channelMap.put(ChannelName.RS_TECHNICAL_CHANNEL, technicalChannel);
        channelMap.put(ChannelName.RS_DAILY_FEED_CHANNEL, dailyFeedChannel);
    }

    public Long getChannelId(ChannelName channelName) {
        final Long channelId = channelMap.get(channelName);

        if (channelId == null) {
            throw new IllegalArgumentException();
        }

        return channelId;
    }
}
