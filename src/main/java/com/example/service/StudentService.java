package com.example.service;

import com.example.dto.PageDTO;
import com.example.dto.SearchDTO;
import com.example.dto.StudentDTO;
import com.example.entity.Student;
import com.example.repository.StudentRepo;
import com.example.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
public class StudentService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    StudentRepo studentRepo;

    public StudentDTO convert(Student student){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); //nếu không để strict, có thể bị conflict giữa User user và int user_id
        return modelMapper.map(student, StudentDTO.class);
    }

    @Transactional
    public void create(StudentDTO studentDTO){
//        User user = new ModelMapper().map(studentDTO.getUser(), User.class);
//        userRepo.save(user);//neu khong dung cascade, can save user truoc
//        Student student = new Student();
//        student.setUser(user); //phai de la userId de no map vao cot khoa chinh, hoac dung la @MapsId thi co the save truc tiep user
//        student.setStudentCode(studentDTO.getStudentCode());
        Student student = new ModelMapper().map(studentDTO, Student.class);
        studentRepo.save(student);
    }

    @Transactional
    public void update(StudentDTO studentDTO){

        Student student = new ModelMapper().map(studentDTO, Student.class); //map the nay thi khong co Id -> khong lam the nay duoc
        student.setUserId(studentDTO.getUser().getId());
        studentRepo.save(student);
    }

    @Transactional
    public void delete(int id){
        studentRepo.deleteById(id);
    }
//    StudentDTO getById(int id){
//
//    }

    public List<StudentDTO> getAllStudent(){
        List<Student> students = studentRepo.findAll();
        return students.stream().map(u->convert(u)).collect(Collectors.toList());
    }
    public PageDTO<List<StudentDTO>> search(//String name, int currentPage, int size, String sortedField
                                             SearchDTO searchDTO){
        //trang hien tai tinh tu so 0, size la so ban ghi tren mot trang
        Sort sortBy=Sort.by("studentCode").ascending(); //sap xep theo ten va tuoi (mac dinh)

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

        Page<Student> page = studentRepo.searchByStudentCode("%" + searchDTO.getKeyword() +"%", pageRequest);
        PageDTO<List<StudentDTO>> pageDTO = new PageDTO<List<StudentDTO>>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setSize(page.getSize());
        //List<User> users = page.getContent();
        List<StudentDTO> studentDTOS = page.get().map(u->convert(u)).collect(Collectors.toList());

        //T: List<UserDTO>
        pageDTO.setData(studentDTOS);
        return pageDTO;
    }
}
