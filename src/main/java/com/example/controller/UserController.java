package com.example.controller;

import com.example.dto.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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


    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") int id){
        userService.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/edit")
    public String updateUser(@RequestParam("id") int id, Model model){
        User user = userService.getById(id);
        model.addAttribute("user", user); //day thong tin user qua view
        return "edit-user.html";
    }

    @PostMapping("/user/edit")
    public String updateUser(@ModelAttribute User user){
        userService.update(user);
        return "redirect:/user/list";
    }

    @PostMapping("/user/search")
    public String search(Model model, @RequestParam("keyword") String keyword){
        List<User> users = userService.searchName(keyword);
        model.addAttribute("userList",users);
        return "users.html";
    }
}
