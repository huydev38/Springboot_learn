package com.example.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
    @Value("${jwt.secret:123}") //123 la default, neu khong tim duoc trong properties
    private String secretKey;
    //cai nay dong vai tro khi gui token len server phai dung de check nguoc lai
    //can phai giu bi mat secretKey

    private long validity = 5; //5phut

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);
        //claims.put("", object) nếu muốn lưu thêm thông tin dùng nnay
        Date now = new Date();
        Date exp = new Date(now.getTime() + validity * 60 * 1000);

        return Jwts.builder().setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secretKey) //Algorithm số càng cao thuật toán càng khó
                .compact();
    }

    //ham check xem token con hieu luc khong
    public boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            //do nothing
        }
        return false;
    }

    public String getUsername(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            //do nothing
        }
        return "NOT FOUND";
    }
}