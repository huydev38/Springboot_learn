package com.example.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAOP {

    //chặn trước khi gọi đến hàm này
    @Before("execution(* com.example.service.UserService.getById(*))") //dau * the hien 1 bien truyen vao, vi tri 0
    public void getByUserId(JoinPoint joinPoint){
        int id = (Integer) joinPoint.getArgs()[0];  //lấy ra biến, vi o day chi truyen vao 1 bien id nen args[0] chinh la id
        log.info("JOINT POINT: "+id);
    }
}
