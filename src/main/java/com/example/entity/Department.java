package com.example.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Data

//tu gen ngay tao, ngay update
@EntityListeners(AuditingEntityListener.class)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @CreatedDate
    @Column(updatable = false)
    private Date createdAt; //java.util

    @LastModifiedDate
    private Date updatedAt;

    //Khong bat buoc
    //one department t many users
    //mappedBy la ten thuoc tinh o entity user
    @OneToMany(mappedBy = "department")
    private List<User> users;
}
