package com.example.service;

import com.example.dto.CourseDTO;

import com.example.dto.PageDTO;
import com.example.dto.SearchDTO;
import com.example.dto.StudentDTO;
import com.example.entity.Course;
import com.example.entity.Student;
import com.example.repository.CourseRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    CourseRepo courseRepo;

    private CourseDTO convert(Course course){

        return new ModelMapper().map(course, CourseDTO.class);
    }

    @Transactional
    public void create(CourseDTO courseDTO){
        Course course = new ModelMapper().map(courseDTO, Course.class);
        courseRepo.save(course);
    }

    @Transactional
    public void update(CourseDTO courseDTO) {
        if (courseRepo.findById(courseDTO.getId()).isPresent()) {
            Course course = new ModelMapper().map(courseDTO, Course.class);
            courseRepo.save(course);
        }
    }

    @Transactional
    public void delete(int id){
        courseRepo.deleteById(id);
    }

    public PageDTO<List<CourseDTO>> search(//String name, int currentPage, int size, String sortedField
                                            SearchDTO searchDTO){
        //trang hien tai tinh tu so 0, size la so ban ghi tren mot trang
        Sort sortBy=Sort.by("id").ascending(); //sap xep theo ten va tuoi (mac dinh)

        //sort theo yeu cau
        if(StringUtils.hasText(searchDTO.getSortedField())){ //check xem co empty khong
            sortBy=Sort.by(searchDTO.getSortedField());
        }
        if(searchDTO.getCurrentPage()==null){
            searchDTO.setCurrentPage(0);
        }
        if(searchDTO.getSize()==null){
            searchDTO.setSize(20);
        }

        //tao PageRequest de truyen vao Pageable
        PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(),searchDTO.getSize(),sortBy);

        Page<Course> page = courseRepo.searchByName("%" + searchDTO.getKeyword() +"%", pageRequest);
        PageDTO<List<CourseDTO>> pageDTO = new PageDTO<List<CourseDTO>>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setSize(page.getSize());
        //List<User> users = page.getContent();
        List<CourseDTO> courseDTOS = page.get().map(u->convert(u)).collect(Collectors.toList());

        //T: List<UserDTO>
        pageDTO.setData(courseDTOS);
        return pageDTO;
    }

}
