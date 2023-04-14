package com.example.service;


import com.example.dto.User;
import com.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service //tao bean: new Uservice, quan ly boi SpringContainer, tao truoc bean
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Transactional //transaction, dam bao cho thuc hien thanh cong, neu khong thi rollback
    public void create(User user){
        userRepo.save(user);
    }
    public  ArrayList<User> getAll(){
         return (ArrayList<User>) userRepo.findAll();
    }

    //co san deleteById()
    @Transactional
    public void delete(int id){
        userRepo.deleteById(id);
    }
    //co san save()
    //check xem ton tai ban ghi chua
    @Transactional
    public void update(User user){
        User currentUser = userRepo.findById(user.getId()).orElse(null);
        if(currentUser!=null){
            currentUser.setName(user.getName());
            currentUser.setAge(user.getAge());
            userRepo.save(currentUser);

        }
    }

    //findById tra ve kieu Optional, them orElse: neu tim thay tra ve User, khong thi tra ve null
    public User getById(int id){
        return userRepo.findById(id).orElse(null);
    }

    public List<User> searchName(String name){
        return userRepo.searchByName(name);

    }
}

