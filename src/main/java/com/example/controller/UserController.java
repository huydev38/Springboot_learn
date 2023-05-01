package com.example.controller;

import com.example.dto.DepartmentDTO;
import com.example.dto.PageDTO;
import com.example.dto.SearchDTO;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.service.DepartmentService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
public class UserController {
//    UserService userService = new UserService();
    @Autowired  //map bean tao truoc vao bien aka DI
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/user/list")
    public String list(Model model){
        List<UserDTO> users = userService.getAll();
        model.addAttribute("userList",users);
        model.addAttribute("searchDTO", new SearchDTO());
        return "users.html";
    }
    @GetMapping("/user/new")
    public String newUser(Model model){
        PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
        model.addAttribute("departmentList", pageDTO.getData());
        model.addAttribute("user", new UserDTO());
        return "new-user.html";
    }

    @PostMapping("user/new")
    public String newUser(@ModelAttribute("user") @Valid UserDTO userDTO,
                          BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
            model.addAttribute("departmentList", pageDTO.getData());
            return "new-user.html";
        }

        if(!userDTO.getFile().isEmpty()){
            String filename = userDTO.getFile().getOriginalFilename();
            File saveFile = new File("D:\\java_backend_learn\\demo\\src\\main\\resources\\static\\img\\" + filename);
            try {
                userDTO.getFile().transferTo(saveFile);
                userDTO.setAvatarURL(filename); //luu file xuong db
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userService.create(userDTO);
        return "redirect:/user/list"; //GET
    }

    //Download file
    @GetMapping("/user/download")//?filename=abc.jpg
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception{
        File file = new File("src/main/resources/static/img/"+filename);
        Files.copy(file.toPath(), resp.getOutputStream());
    }


    @GetMapping("/user/delete")
    public String deleteUser(@RequestParam("id") int id){
        userService.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/edit")
    public String updateUser(@RequestParam("id") int id, Model model){
        UserDTO user = userService.getById(id);
        model.addAttribute("user", user); //day thong tin user qua view
        PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
        model.addAttribute("departmentList", pageDTO.getData());
        return "edit-user.html";
    }

    @PostMapping("/user/edit")
    public String updateUser(@ModelAttribute UserDTO userDTO, @RequestParam(name="file", required = false)MultipartFile uploadFile){
        if(!uploadFile.isEmpty()){
            String filename = uploadFile.getOriginalFilename();
            File saveFile = new File("D:\\java_backend_learn\\demo\\src\\main\\resources\\static\\img\\" + filename);
            try {
                uploadFile.transferTo(saveFile);
                userDTO.setAvatarURL(filename); //luu file xuong db
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userService.update(userDTO);
        return "redirect:/user/list";
    }

    @GetMapping("/user/search")
    public String search(Model model,
//
                         @ModelAttribute @Valid SearchDTO searchDTO, BindingResult bindingResult)
                         {
                    //check xem co loi binding khong
                             if(bindingResult.hasErrors()){
                                 PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
                                 model.addAttribute("departmentList", pageDTO.getData());
                                 return "users.html";
                             }

        PageDTO<List<UserDTO>> pageUser= userService.searchName(searchDTO);
        model.addAttribute("userList",pageUser.getData()); //tra ve list
        model.addAttribute("totalPage", pageUser.getTotalPages());
        model.addAttribute("totalElements",pageUser.getTotalElements());
        model.addAttribute("size", pageUser.getSize());

                             model.addAttribute("searchDTO",searchDTO);
        return "users.html";
    }
}
