package com.ssafy.backend.controller;

import com.ssafy.backend.model.User;
import com.ssafy.backend.repository.UserRepository;
import com.ssafy.backend.util.UserSha256;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @ApiOperation(value="회원 가입")
    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
//        repository.insert(new User("ssafy@ssafy.com", "ssafy", "010-0000-0000", "김싸피", 0));
        user.setPassword(UserSha256.encrypt(user.getPassword()));
        repository.insert(user);
    }

    @ApiOperation(value="로그인")
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User findUser = repository.findByEmail(user.getEmail());
//        System.out.println(findUser);
//        System.out.println(findUser.getPassword());
//        System.out.println(UserSha256.encrypt(user.getPassword()));
        if (findUser.getPassword().equals(UserSha256.encrypt(user.getPassword()))) {
//            System.out.println("1");
            return findUser;
        } else {
//            System.out.println("2");
            return null;
        }
    }

    @ApiOperation(value="회원 정보 수정")
    @PostMapping("/update")
    public void updateUserInfo(@RequestBody User user) {
        repository.save(user); //덮어씌우므로 user에 모든 정보가 포함되어야 함...
    }

    /* 이상이상....
    @ApiOperation(value="회원 정보 조회")
    @PostMapping("/")
    public User findById(@RequestBody User user) {
        Optional<User> op = repository.findById(user.getId());
        return op.get();
    }
    */

}
