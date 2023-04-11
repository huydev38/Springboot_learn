package com.example.service;


import com.example.dto.User;
import com.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
@Service //tao bean: new Uservice, quan ly boi SpringContainer, tao truoc bean
public class UserService {
//    private  ArrayList<User> list = new ArrayList<>();
    @Autowired
    UserRepo userRepo;
//    public void create(User user) {
//        list.add(user);
//    }
//
//    public  ArrayList<User> getAll(){
//        return list;
//    }

    @Transactional //transaction, dam bao cho thuc hien thanh cong, neu khong thi rollback
    public void create(User user){
        userRepo.save(user);
    }
    public  ArrayList<User> getAll(){
         return (ArrayList<User>) userRepo.findAll();
    }
}

