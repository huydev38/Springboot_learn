package com.example.controller;

import com.example.dto.SearchDTO;
import com.example.dto.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;


import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public String newUser(@ModelAttribute User user,
                          @RequestParam(name="file", required = false)MultipartFile uploadFile //upload file
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
        if(!uploadFile.isEmpty()){
            String filename = uploadFile.getOriginalFilename();
            File saveFile = new File("D:/java_backend_learn/demo/src/main/resources/static/img" + filename);
            try {
                uploadFile.transferTo(saveFile);
                user.setAvatarURL(filename); //luu file xuong db
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userService.create(user);
        return "redirect:/user/list"; //GET
    }

    //Download file
    @GetMapping("/user/download")//?filename=abc.jpg
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception{
        File file = new File("D:/java_backend_learn/demo/src/main/resources/static/img"+filename);
        Files.copy(file.toPath(), resp.getOutputStream());
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

    @GetMapping("/user/search")
    public String search(Model model, //qua nhieu param, tao 1 class moi, dung ModelAttribute
//                         @RequestParam("keyword") String keyword,
//                         @RequestParam(name="currentPage", required = false) Integer currentPage,
//                         @RequestParam(name="size",required = false) Integer size,
//                         @RequestParam(name="sortedField", required = false) String sortedField)
                         @ModelAttribute SearchDTO searchDTO)
                         {

//        List<User> users = userService.searchName(keyword);
        //thay vi dung List, dung Page
        //de la String de check null, neu khong nhap thi la null, dung the nay de tranh exception

//        currentPage = currentPage==null?0:currentPage;
//        size= (size==null?20:size);
        Page<User> pageUser= userService.searchName(searchDTO);
        model.addAttribute("userList",pageUser.getContent()); //tra ve list
        model.addAttribute("totalPage", pageUser.getTotalPages());
        model.addAttribute("totalElements",pageUser.getTotalElements());
        model.addAttribute("size", pageUser.getSize());
//        model.addAttribute("currentPage", currentPage);
////        model.addAttribute("keyword",keyword);
////        model.addAttribute("sortedField",sortedField);
                             model.addAttribute("searchDTO",searchDTO);
        return "users.html";
    }
}
