package com.example.service;


import com.example.dto.PageDTO;
import com.example.dto.SearchDTO;
import com.example.dto.UserDTO;
import com.example.entity.User;
import com.example.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service //tao bean: new Uservice, quan ly boi SpringContainer, tao truoc bean
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    private UserDTO convert(User user){
        //co the set tung cai nhu
        //User user = new User
        //user.setName(userDTO.getName());
        return new ModelMapper().map(user, UserDTO.class);
    }
    @Transactional //transaction, dam bao cho thuc hien thanh cong, neu khong thi rollback
    public void create(UserDTO userDTO){
        User user = new ModelMapper().map(userDTO, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); //truoc khi save vao db, phai encrypt truoc
        userRepo.save(user);
    }
    public  List<UserDTO> getAll(){
        List<User> userList=userRepo.findAll();
         return userList.stream().map(u->convert(u)).collect(Collectors.toList());
    }

    //co san deleteById()
    @Transactional
    @CacheEvict(cacheNames = "user", key = "#id")
    public void delete(int id){
        userRepo.deleteById(id);
    }
    //co san save()
    //check xem ton tai ban ghi chua
    @Transactional
    @CacheEvict(cacheNames = "user", key = "#userDTO.id")
    public void update(UserDTO userDTO){
        User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
        if(currentUser!=null){
            currentUser.setName(userDTO.getName());
            currentUser.setAge(userDTO.getAge());
            currentUser.setAvatarURL(userDTO.getAvatarURL());
            currentUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            userRepo.save(currentUser);

        }
    }

    //findById tra ve kieu Optional, them orElse: neu tim thay tra ve User, khong thi tra ve null

    @Cacheable(cacheNames = "user", key="#id") //nếu key ở đây để trống, key sẽ tự lấy id (vì nó là biến truyền vào làm id
    public UserDTO getById(int id){
        User user = userRepo.findById(id).orElse(null);
        if(user!=null){
            return convert(user);
        }
        return null;
    }

    public List<UserDTO> searchName(String name){
        return userRepo.searchByName(name).stream().map(u->convert(u)).collect(Collectors.toList());

    }
    public PageDTO<List<UserDTO>> searchName(//String name, int currentPage, int size, String sortedField
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
        PageDTO<List<UserDTO>> pageDTO = new PageDTO<List<UserDTO>>();
        pageDTO.setTotalPages(page.getTotalPages());
        pageDTO.setTotalElements(page.getTotalElements());
        pageDTO.setSize(page.getSize());
        //List<User> users = page.getContent();
        List<UserDTO> userDTOS = page.get().map(u->convert(u)).collect(Collectors.toList());

        //T: List<UserDTO>
        pageDTO.setData(userDTOS);
        return pageDTO;
    }

    //lay ra UserDetails tu database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepo.findByUsername(username);
        if(userEntity==null){
            throw new UsernameNotFoundException("Not Found");
        }
        //convert user->userdetails
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(String role:userEntity.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return new org.springframework.security.core.userdetails.User(username,
                userEntity.getPassword(),authorities);
    }
}

