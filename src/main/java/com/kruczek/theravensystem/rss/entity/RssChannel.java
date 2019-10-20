package com.kruczek.theravensystem.rss.entity;

import com.kruczek.theravensystem.rss.RssCategory;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity( name = "rss_channels")
public class RssChannel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "rss_type")
    private RssCategory rssType;

}
