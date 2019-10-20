package com.kruczek.theravensystem.rss;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RssNewsView {
    private String title;
    private String desc;
    private String url;
}
