package com.bhargav.firstspringbootapp.controller;

/*
* This is the initial project in the E-commerce spring boot with microservices course
* This is a simple project has simple get and post call
* */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping("/hello")
    public String fecthData(){
        return "Hello, there!";
    }

    @PostMapping("/hello")
    public String postData(@RequestBody String name){
        return "Hello " + name + "!!!";
    }
}
