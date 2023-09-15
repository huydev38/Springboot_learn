package com.example.controller;

import com.example.dto.*;
import com.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/")
    public ResponseDTO<Void> newStudent(@RequestBody @Valid StudentDTO studentDTO){

        studentService.create(studentDTO);
        return ResponseDTO.<Void>builder().status(200).msg("Success").build();
    }

    @PutMapping("/")
    public ResponseDTO<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO){
        studentService.update(studentDTO);
        return ResponseDTO.<StudentDTO>builder().status(200).msg("Success").data(studentDTO).build();
    }


    @DeleteMapping("/")
    public ResponseDTO<Void> deleteStudent(@RequestParam("id")int id){
        studentService.delete(id);
        return ResponseDTO.<Void>builder().status(200).msg("Success").build();
    }
    @PostMapping("/list")
    public ResponseDTO<PageDTO<List<StudentDTO>>> search(
//
            @ModelAttribute @Valid SearchDTO searchDTO)
    {


        PageDTO<List<StudentDTO>> pageStudent= studentService.search(searchDTO);

        return ResponseDTO.<PageDTO<List<StudentDTO>>>builder().msg("Success").data(pageStudent).status(200).build();
    }
}
