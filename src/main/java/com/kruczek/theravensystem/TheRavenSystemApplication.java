package com.kruczek.theravensystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TheRavenSystemApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(TheRavenSystemApplication.class, args);
    }
}
