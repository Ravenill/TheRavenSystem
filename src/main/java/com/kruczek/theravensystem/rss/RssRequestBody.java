package com.kruczek.theravensystem.rss;

import lombok.Data;

@Data
public class RssRequestBody {
    private String url;
    private RssCategory category = RssCategory.OTHER;
}
