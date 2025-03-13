package com.example.flamingosession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlamingoSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlamingoSessionApplication.class, args);
    }

}
