package com.example.repository;

import com.example.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

    @Query("SELECT d FROM Department d where d.name LIKE :x")
    Page<Department> searchName(@Param("x") String name, Pageable pageable);


}
