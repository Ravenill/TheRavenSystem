package com.kruczek.theravensystem.config;

import com.kruczek.theravensystem.RavenSystemCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@EnableScheduling
public class SchedulerConfig {

    private RavenSystemCore ravenSystemCore;

    public SchedulerConfig(RavenSystemCore ravenSystemCore) {
        this.ravenSystemCore = ravenSystemCore;
    }

    @Scheduled(fixedDelayString = "${rss.news.valid.in.millis:1800000}")
    public void executeRssTask() {
        log.info("Executing RSS task: " + LocalDateTime.now());
        ravenSystemCore.fetchAndSendRssFeed();
    }
}
