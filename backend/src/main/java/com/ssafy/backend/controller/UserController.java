package com.ssafy.backend.controller;

import com.ssafy.backend.model.UserDto;
import com.ssafy.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @RequestMapping("/signup")
    public void insert(@RequestBody UserDto user) {

    }

    public void deleteById(int id) {

    }

    @RequestMapping("/signin")
    public void login(@PathVariable String id) {

    }

}
