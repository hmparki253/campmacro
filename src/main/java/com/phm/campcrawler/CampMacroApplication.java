package com.phm.campcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CampMacroApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampMacroApplication.class, args);
    }
}
