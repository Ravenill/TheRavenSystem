package com.kruczek.theravensystem.config;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kruczek.theravensystem.RavenSystemCore;

import lombok.extern.log4j.Log4j;

@Log4j
@Component
@EnableScheduling
class SchedulerConfig {

    private final RavenSystemCore ravenSystemCore;

    @Autowired
    SchedulerConfig(RavenSystemCore ravenSystemCore) {
        this.ravenSystemCore = ravenSystemCore;
    }

    @Scheduled(fixedDelayString = "${rss.news.valid.in.millis:1800000}")
    void executeRssTask() {
        log.info("Executing RSS task: " + LocalDateTime.now());
        ravenSystemCore.fetchAndSendRssFeed();
    }
}
