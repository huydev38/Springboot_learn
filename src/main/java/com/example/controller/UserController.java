package com.example.controller;

import com.example.dto.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
public class UserController {
//    UserService userService = new UserService();
    @Autowired  //map bean tao truoc vao bien aka DI
    UserService userService;
    @GetMapping("/user/list")
    public String list(Model model){
        List<User> users = userService.getAll();
        model.addAttribute("userList",users);
        return "users.html";
    }
    @GetMapping("/user/new")
    public String newUser(){
        return "new-user.html";
    }

    @PostMapping("user/new")
    public String newUser(@ModelAttribute User user
                          //map thang vao user, khong can dung Param, luu y dung trong truong hop Param
                          //giong voi cac attribute cua user

//            @RequestParam("id")int id,
//            @RequestParam("name") String name,
//            @RequestParam("age") int age
    ){
//        User user = new User();
//        user.setId(id);
//        user.setName(name);
//        user.setAge(age);
        userService.create(user);
        return "redirect:/user/list"; //GET
    }
}
