package com.example;

import com.example.interceptor.LogInterceptor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing

@EnableScheduling //dung de chay scheduler

@EnableCaching  //redis

public class DemoApplication implements WebMvcConfigurer {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Autowired
    LogInterceptor logInterceptor;


    //them interceptor
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }

    //tao topic
    @Bean
    NewTopic login(){
        return new NewTopic("login", 1, (short) 1);
    }
}
