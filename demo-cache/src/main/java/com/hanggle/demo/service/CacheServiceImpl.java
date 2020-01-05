package com.hanggle.demo.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CacheServiceImpl implements CacheService{

    @Cacheable(value = "wwww", key = "'_' + #reasonCode", sync = true)
    public List<String> defeatResonByCode(String reasonCode) {
        String name = "list1";
        System.out.println("service");
        List<String> strings = Arrays.asList(name, reasonCode);
        return strings;
    }

    @Cacheable(value = "wwwwss", key = "'__' + #reasonCode", sync = true)
    @Override
    public String getTemp(String reasonCode) {
        Temp temp = new Temp(1, reasonCode);
        return temp.toString();
    }
}
