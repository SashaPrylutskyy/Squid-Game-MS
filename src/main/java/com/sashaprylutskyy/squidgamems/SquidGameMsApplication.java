package com.sashaprylutskyy.squidgamems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SquidGameMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SquidGameMsApplication.class, args);
    }

}
