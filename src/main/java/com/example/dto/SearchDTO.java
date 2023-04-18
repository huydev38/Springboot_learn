package com.example.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SearchDTO {

    private String keyword;

    private Integer currentPage;
    private Integer size;
    private String sortedField;
}
