package com.inkognito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class InkognitoDiaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InkognitoDiaryApplication.class, args);
    }

}