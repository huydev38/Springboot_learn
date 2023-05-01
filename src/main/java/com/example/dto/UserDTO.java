package com.example.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
public class UserDTO {
    private int id;
    @NotBlank
    private String name;
    @Min(value=1, message = "{min.msg}")
    private int age;

    private String avatarURL;


    private String username;

    private String password;
    private MultipartFile file;

    //manytoone
    private DepartmentDTO department;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;
}
