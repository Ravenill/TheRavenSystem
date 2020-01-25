package com.kruczek.theravensystem.rss;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class RssNewsView {
    private String title;
    private String desc;
    private String url;

    private Map<String, String> additionalData;
}
