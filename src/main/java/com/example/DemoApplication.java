package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
//cau hinh them ngon ngu
public class DemoApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    //cau hinh them ngon ngu
    //tao bean tu day, check xem ngon ngu dang chon la gi
    @Bean
    public LocaleResolver localeResolver(){
        //tim xem ngon ngu dang chon duoc luu o dau
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        //mac dinh tieng viet
        resolver.setDefaultLocale(new Locale("vi_VN"));
        return resolver;
    }

    //giong filter, doc xem request gui len co language khong, request nao chay len cung chay qua cai nay
    @Bean
    LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("language");
        return lci;
    }

    //add vao luong cua spring de doc
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
