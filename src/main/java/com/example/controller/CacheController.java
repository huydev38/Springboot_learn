package com.example.controller;

import com.example.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    CacheManager cacheManager;

    //lay ra danh sach cache
    @GetMapping("/")
    public List<String> getCaches(){
       return cacheManager.getCacheNames().stream().collect(Collectors.toList());
    }

    //xoa cache
    @DeleteMapping("/")
    public ResponseDTO<Void> delete(@RequestParam("name") String name){
        Cache cache = cacheManager.getCache(name);
        if(cache != null){
            cache.clear();
        }
        return ResponseDTO.<Void>builder().status(200).build();
    }

}
