package com.example;


import com.example.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component

/* dung filter nay de doc token, lay ra username, check xem valid khong, lay duoc user roi thi set Authentication vao context,
he thong se nhan la co dang nhap roi */
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenService jwtTokenService;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{
        String bearerToken = request.getHeader("Authorization");
        log.info(bearerToken);

        //bearer la token chua thong tin bao mat
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            String token = bearerToken.substring(7); //lấy ra sau từ Bearer
            String username = jwtTokenService.getUsername(token);
            if(username!= null){
                //token valid, create a authentication

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());

                //gia lap security, set vao nnay de xem nhu da dang nhap
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
