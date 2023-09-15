package com.example.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;


@Data
public class UserDTO {
    private int id;
    @NotBlank
    private String name;
    private int age;

    private String avatarURL;


    private String username;

    private String password;

    @JsonIgnore //để khi show thành json nó bỏ qua, tuy nhiên khi map file vào thì vẫn có
    private MultipartFile file;

    private List<String> roles;

    @JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthdate;

}
