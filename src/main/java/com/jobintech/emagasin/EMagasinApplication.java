package com.jobintech.emagasin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EMagasinApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMagasinApplication.class, args);

    }

}
