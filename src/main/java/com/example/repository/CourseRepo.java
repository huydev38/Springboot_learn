package com.example.repository;

import com.example.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepo extends JpaRepository<Course, Integer> {
    @Query("SELECT u from Course u  WHERE u.name LIKE :x")
    Page<Course> searchByName(@Param("x") String s, Pageable pageable);
}
