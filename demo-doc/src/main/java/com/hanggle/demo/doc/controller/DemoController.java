package com.hanggle.demo.doc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/demo")
@RestController
public class DemoController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
