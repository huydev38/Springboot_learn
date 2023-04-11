package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hi(){
        //map url vao 1 function, tra ve ten file view
        return "hi.html";
        //dung thymeleaf nen se dung html
    }
}
