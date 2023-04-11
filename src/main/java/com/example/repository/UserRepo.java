package com.example.repository;

import com.example.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

//khi chay se tao them 1 row trong table, User la ten Entity, Integer la kieu cua id
//Tu tao bean
public interface UserRepo extends JpaRepository<User, Integer> {
}
