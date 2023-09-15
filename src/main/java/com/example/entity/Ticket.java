package com.example.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String clientName;

    private String clientPhone;

    private String content;

    private boolean status;

    @CreatedDate
    private Date createdAt;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date processDate;

    @ManyToOne
    private Department department;
}
