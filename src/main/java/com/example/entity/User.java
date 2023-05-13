package com.example.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

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

    private String email;

    @ElementCollection
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
    @Column(name="role")
    private List<String> roles;

    @Temporal(TemporalType.DATE)
    private Date birthdate;

//    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
//    private Student student;
}

