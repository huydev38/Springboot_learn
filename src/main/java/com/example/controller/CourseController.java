package com.example.controller;

import com.example.dto.*;

import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @PostMapping("/")
    public ResponseDTO<Void> newCourse(@RequestBody CourseDTO courseDTO){
        courseService.create(courseDTO);
        return ResponseDTO.<Void>builder().msg("Success").status(200).build();
    }

    @DeleteMapping("/")
    public ResponseDTO<Void> delete(@RequestParam("id") int id) {
        courseService.delete(id);
        return ResponseDTO.<Void>builder().msg("Success").status(200).build();
    }

    @PutMapping("/")
    public ResponseDTO<CourseDTO> update(@RequestBody CourseDTO courseDTO){
        courseService.update(courseDTO);
        return ResponseDTO.<CourseDTO>builder().msg("Success").status(200).data(courseDTO).build();
    }

    @PostMapping("/search")
    public ResponseDTO<PageDTO<List<CourseDTO>>> search(@ModelAttribute SearchDTO searchDTO){
        PageDTO<List<CourseDTO>> page= courseService.search(searchDTO);

        return ResponseDTO.<PageDTO<List<CourseDTO>>>builder().data(page).msg("Success").status(200).build();
    }
}
