package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)   //sử dụng bảo mật trên hàm
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    //cau hinh qua trinh xac thuc
    @Autowired  //vi trong Spring co san /AuthenticationManagerBuilder/ nen minh Autowired no se lay Authen co san
    public void config(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("test")
//                .password(new BCryptPasswordEncoder().encode("123456")) //ham nay dung de encode password
//                .authorities("ADMIN")
//                .and().passwordEncoder(new BCryptPasswordEncoder()); //encode voi password nhap vao xem trung nhau khong

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //phan quyen
    @Bean
    public SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**","/user/**") //2 ** nghia la moi duong dan dang sau deu ap dung, * chi ap dung neu co 1 duong dan dang sau
                .hasAnyAuthority("ADMIN")  //yeu cau dang nhap vao co role la ADMIN
                .antMatchers("/member/**")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().httpBasic()
                .and().csrf().disable();
        return http.build();
    }

}
