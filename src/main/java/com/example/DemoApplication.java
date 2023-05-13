package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing

@EnableScheduling //dung de chay scheduler
//cau hinh them ngon ngu
public class DemoApplication implements WebMvcConfigurer {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
