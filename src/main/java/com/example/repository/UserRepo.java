package com.example.repository;

import com.example.dto.UserDTO;
import com.example.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//khi chay se tao them 1 row trong table, User la ten Entity, Integer la kieu cua id
//Tu tao bean
public interface UserRepo extends JpaRepository<User, Integer> {
    //tim theo username
    // khong can viet ham, dung findBy+column muon tim
    //Jpa se tu gen code SELECT de tim
    //findBy giong dau "=" trong cau sau select user where username = ?
    //viet hoa chu dau
    User findByUsername(String username);

    List<User> findByName(String s);

    //Pageable trong thu vien springboot.data.domain
    Page<User> findByName(String s, Pageable pageable);

    @Query("SELECT u from User u  WHERE u.name LIKE :x")
        //Select tren Entity / tren class User chu khong phai tren bang
            //User la ten ENTITIES
    //day la JPQL
    //bang voi SELECT * FROM user WHERE name =
        // thay vi dung ?, dung :x nhu placeholder, (x la bien, co the doi thanh ki tu khac, vd :y)
        // String s la tham so truyen vao
    List<User> searchByName(@Param("x") String s);
    @Query("SELECT u from User u  WHERE u.name LIKE :x")
    Page<User> searchByName(@Param("x") String s, Pageable pageable);


    //method tuy bien
    @Query("SELECT u FROM User u WHERE u.name LIKE :x OR u.username LIKE :x")
    List<User> searchByNameAndUsername(@Param("x")String s);

    //neu dung INSERT, UPDATE, DELETE thi phai @Modifying
    @Modifying
    @Query("DELETE FROM User u WHERE u.username= :x")
    int deleteUser(@Param("x") String username);

    //tu gen lenh xoa
    void deleteByUsername(String username);
}
