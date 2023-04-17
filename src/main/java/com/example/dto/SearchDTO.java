package com.example.dto;

import lombok.Builder;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Default;

@Data
public class SearchDTO {
    private String keyword;
    private Integer currentPage;
    private Integer size;
    private String sortedField;
}
