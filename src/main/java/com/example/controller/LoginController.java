package com.example.controller;

import com.example.dto.ResponseDTO;
import com.example.service.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    //authen dung AuthenticationManager, tao bean o security
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseDTO<String> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(username, password)));
        //cai nay dung userdetail de check



        return ResponseDTO.<String>builder().data(jwtTokenService.createToken(username)).status(200).build(); //goi den ham createToken

    }
}
