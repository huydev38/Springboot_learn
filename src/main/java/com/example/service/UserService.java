package com.example.service;


import com.example.dto.SearchDTO;
import com.example.dto.User;
import com.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service //tao bean: new Uservice, quan ly boi SpringContainer, tao truoc bean
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Transactional //transaction, dam bao cho thuc hien thanh cong, neu khong thi rollback
    public void create(User user){
        userRepo.save(user);
    }
    public  ArrayList<User> getAll(){
         return (ArrayList<User>) userRepo.findAll();
    }

    //co san deleteById()
    @Transactional
    public void delete(int id){
        userRepo.deleteById(id);
    }
    //co san save()
    //check xem ton tai ban ghi chua
    @Transactional
    public void update(User user){
        User currentUser = userRepo.findById(user.getId()).orElse(null);
        if(currentUser!=null){
            userRepo.save(user);

        }
    }

    //findById tra ve kieu Optional, them orElse: neu tim thay tra ve User, khong thi tra ve null
    public User getById(int id){
        return userRepo.findById(id).orElse(null);
    }

    public List<User> searchName(String name){
        return userRepo.searchByName(name);

    }
    public Page<User> searchName(//String name, int currentPage, int size, String sortedField
                                 SearchDTO searchDTO){
        //trang hien tai tinh tu so 0, size la so ban ghi tren mot trang
        Sort sortBy=Sort.by("name").ascending().and(Sort.by("age").descending()); //sap xep theo ten va tuoi (mac dinh)

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

        Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() +"%", pageRequest);
        System.out.println("So page la"+ page.getTotalPages()+"So phan tu la" + page.getTotalElements());
        return page;
    }
}

