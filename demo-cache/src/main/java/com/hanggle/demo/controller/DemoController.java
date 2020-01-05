package com.hanggle.demo.controller;

import com.hanggle.demo.service.CacheService;
import com.hanggle.demo.service.Temp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/cache")
    public List<String> hello(@RequestParam String code) {
        List<String> strings = cacheService.defeatResonByCode(code);
        return strings;
    }
    @GetMapping("/cache2")
    public String cache2(@RequestParam String code) {
         String strings = cacheService.getTemp(code);
        return strings;
    }
}
