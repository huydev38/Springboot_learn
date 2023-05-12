package com.example.controller;

import com.example.dto.PageDTO;
import com.example.dto.ResponseDTO;
import com.example.dto.SearchDTO;
import com.example.dto.UserDTO;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
//    UserService userService = new UserService();
    @Autowired  //map bean tao truoc vao bien aka DI
    UserService userService;


    @GetMapping("/list")
    public ResponseDTO<List<UserDTO>> list(){
        List<UserDTO> users = userService.getAll();
        return ResponseDTO.<List<UserDTO>>builder().status(200).msg("Success").data(users).build();
    }

    @PostMapping("/")
    public ResponseDTO<Void> newUser(@ModelAttribute("user") @Valid UserDTO userDTO) throws IOException {

        if(!userDTO.getFile().isEmpty()){
            String filename = userDTO.getFile().getOriginalFilename();
            File saveFile = new File("D:\\java_backend_learn\\demo\\src\\main\\resources\\static\\img\\" + filename);

                userDTO.getFile().transferTo(saveFile);
                userDTO.setAvatarURL(filename); //luu file xuong db
        }
        userService.create(userDTO);
        return ResponseDTO.<Void>builder().status(200).msg("Success").build();
    }

    //Download file
    @GetMapping("/download")//?filename=abc.jpg
    public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception{
        File file = new File("src/main/resources/static/img/"+filename);
        Files.copy(file.toPath(), resp.getOutputStream());
    }


    @DeleteMapping("/")
    public ResponseDTO<Void> deleteUser(@RequestParam("id") int id){
        userService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("Success").build();
    }



    @PutMapping("/")
    public ResponseDTO<UserDTO> updateUser(@ModelAttribute UserDTO userDTO, @RequestParam(name="file", required = false)MultipartFile uploadFile){
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
        return ResponseDTO.<UserDTO>builder().status(200).msg("Success").data(userDTO).build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<UserDTO>>> search(
//
                         @ModelAttribute @Valid SearchDTO searchDTO)
                         {


        PageDTO<List<UserDTO>> pageUser= userService.searchName(searchDTO);

        return ResponseDTO.<PageDTO<List<UserDTO>>>builder().msg("Success").data(pageUser).status(200).build();
    }
}
