package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

//ORM framework: Object - Record table
//JPA - Hibernate
//Data - de gen ham getter setter
@Data
@Entity //Map class User với bảng
//@Table(name = "user") //map vao bang user, neu class giong ten table, bo di cung duoc
//MAP
public class User {

    @Id //khoa chinh
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // neu dung AutoIncrement, neu khong dung AutoIncrement thi khong map duoc -> loi
    private int id;

    private String name;
    private int age;

    //luu ten file path
    private String avatarURL;
    //neu dat ten cot trong db khac, thi dung column de map
    //@Column(name="u_name")
    //private String username;


    //them rang buoc unique
    @Column(unique = true)
    private String username;

    private String password;

    @ManyToOne
    //Many user to one department
    //@JoinColumn(name = "department_id"); chi dung voi ManyToOne, neu ten giong thi khong can
    private Department department;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

}

