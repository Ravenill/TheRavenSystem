package com.kruczek.theravensystem.rss.enchant;

import com.kruczek.theravensystem.rss.RssNewsView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ViewEnchanter {
    public List<RssNewsView> enchant(List<RssNewsView> rssNewsViews) {
        return rssNewsViews;
    }
}
