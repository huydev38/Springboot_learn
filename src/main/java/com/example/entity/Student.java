package com.example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Data
public class Student {
    @Id
    private int userId; //studentId sẽ trùng với user_id vì là quan hệ 1 - 1, nên nếu để là userId thì trong db sẽ chỉ sinh ra 1 cột (hợp lý)

    @OneToOne
    @PrimaryKeyJoinColumn //dung chinh cot khoa chinh lam cot join (vừa là PR vừa là FK)
    private User user; //user_id

    private String studentCode;
}
