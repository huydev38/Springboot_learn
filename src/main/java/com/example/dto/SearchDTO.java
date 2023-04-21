package com.example.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SearchDTO {
    //dung de validate
    @NotBlank(message="Not Blank")
    //do dai truong nhap vao
    @Size(min=1, max = 20)
    private String keyword;

    private Integer currentPage;
    private Integer size;
    private String sortedField;
}
