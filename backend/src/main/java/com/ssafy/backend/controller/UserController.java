package com.ssafy.backend.controller;

import com.ssafy.backend.model.User;
import com.ssafy.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @PostMapping("/signup")
    public void insert(@RequestBody User user) {
//        repository.insert(new User("ssafy@ssafy.com", "ssafy", "010-0000-0000", "김싸피", 0));
        repository.insert(user);
    }

}
