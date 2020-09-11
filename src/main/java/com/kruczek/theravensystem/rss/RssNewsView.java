package com.kruczek.theravensystem.rss;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
public class RssNewsView {
    private String title;
    private String desc;
    private String url;

    private Map<String, String> additionalData;

    @Override
    public String toString() {
        final String moreData = additionalData.entrySet().stream()
                .map(moreDate -> moreDate.getKey() + ": " + moreDate.getValue())
                .collect(Collectors.joining("\n"));

        return title + "\n"
                + desc + "\n\n"
                + moreData + "\n"
                + "URL: " + url;
    }
}
