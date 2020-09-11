package com.kruczek.theravensystem.rss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RssController {

    private final RssService rssService;

    @Autowired
    public RssController(RssService rssService) {
        this.rssService = rssService;
    }

    @PostMapping("rss")
    public void addNewRssChannel(@RequestBody RssRequestBody rssRequestBody) {
        rssService.addSource(rssRequestBody.getUrl(), rssRequestBody.getCategory());
    }

    @DeleteMapping("rss")
    public void addNewRssChannel(@RequestParam(name = "url") String url) {
        rssService.deleteSource(url);
    }

    @GetMapping("rss")
    public @ResponseBody List<String> addNewRssChannel(@RequestParam(name = "category", required = false) RssCategory category) {
        if (category == null) {
            return rssService.getListOfSources();
        }

        return rssService.getListOfSources(category);
    }
}
