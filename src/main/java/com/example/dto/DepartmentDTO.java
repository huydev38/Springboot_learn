package com.example.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
@Data
public class DepartmentDTO {
    private int id;
    @NotBlank(message = "{not.blank}")
    private String name;

    private Date createdAt;
    private Date updatedAt;
}
