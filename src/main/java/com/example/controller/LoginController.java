package com.example.controller;

import com.example.dto.TestKafkaDTO;
import com.example.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
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


    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

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

        ResponseEntity<String> response = remoteLogin(username, password);
        String token = response.getBody();
        log.info(token);

        if(response.getStatusCode()==HttpStatus.OK){
            //thanh cong
            //lay ra thong tin user
            UserDTO userDTO = getMe(token);
            session.setAttribute("token", token);
            session.setAttribute("user",userDTO);

            return "redirect:/hello";
    }
            return "redirect:/login";
    }

    public ResponseEntity<String> remoteLogin(String username, String password){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("username", username);
        requestBody.add("password", password);

        HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        //gui request va nhan response

        return restTemplate.exchange("http://localhost:8080/login",
                HttpMethod.POST, entity, String.class);
    }

    public UserDTO getMe(String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //truyen token vao header dang bearer
        headers.setBearerAuth(token);

        //khong co body de la void
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange("http://localhost:8080/user/me",
                HttpMethod.GET, entity, UserDTO.class);
        //response se tra ve dang UserDTO trong body
        return response.getBody();
    }

    @GetMapping("/test")
    public String testSendEvent(){
        TestKafkaDTO test = new TestKafkaDTO();
        test.setMsg("huy");
        kafkaTemplate.send("login", test);
        return "hi.html";
    }


}
