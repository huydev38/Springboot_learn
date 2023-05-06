package com.example.controller;

import com.example.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import javax.persistence.NoResultException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    //Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({ NoResultException.class })
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseDTO<String> notFound(NoResultException e) {
//		e.printStackTrace();
        //logger.info("INFO", e);
        log.info("INFO", e);
        return ResponseDTO.<String>builder().status(404).msg("No Data").build(); //dung builder lombok
    }

//    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
//    @ResponseStatus(code= HttpStatus.CONFLICT)
//    public String duplicate(Exception e) {
//        log.info("INFO", e);
//        return "Duplicated Data";
//    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    @ResponseStatus(code= HttpStatus.CONFLICT)
    public ResponseDTO<String> duplicate(SQLIntegrityConstraintViolationException e) {
        log.info("INFO", e);
        return ResponseDTO.<String>builder().status(400).msg("Data Duplicated").build();
    }

//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public ResponseEntity<String> badRequest(Exception e){
//        log.info("bad request");
//        return ResponseEntity.badRequest().body("Invalid data");
//    }
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> badRequest(org.springframework.validation.BindException e){
        log.info("bad request");

        //dung cai nay de doc message validation
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        String msg = "";
        for(ObjectError err:errorList){
            FieldError fieldError = (FieldError) err;
            msg+=fieldError.getField()+":"+err.getDefaultMessage()+";";
        }

        return ResponseEntity.badRequest().body("Invalid data");
    }
}

//tra ve kieu object ResponseEntity<String> cung duoc, String cung duoc