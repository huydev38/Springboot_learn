package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AvgScoreByCourse {
    private int courseId;
    private String courseName;
    private Double avg;
}
