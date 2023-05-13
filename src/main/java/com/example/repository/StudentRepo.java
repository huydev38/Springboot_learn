package com.example.repository;

import com.example.entity.Student;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    @Query("SELECT u from Student u  WHERE u.studentCode LIKE :x")
    Page<Student>searchByStudentCode(@Param("x") String s, Pageable pageable);


}
