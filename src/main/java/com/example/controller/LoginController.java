package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @PostMapping("/login")
    public String login(HttpSession session,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password) throws IOException {

//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body = RequestBody.create(mediaType, "username="+username+"&password="+password);
//        Request request = new Request.Builder()
//                .url("http://localhost:8080/login")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        Response response = client.newCall(request).execute();
//
//        //doan nay se log ra token
//        assert response.body() != null;
//        log.info(response.body().string());

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);

        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        //gui request va nhan response
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/login",
                HttpMethod.POST, entity, String.class);

        log.info(response.getBody());


        if(response.getStatusCode()==HttpStatus.OK){
            //thanh cong
            session.setAttribute("username",username);

            return "redirect:/hello";
    }
            return "redirect:/login";
    }

}
