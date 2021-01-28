package com.ssafy.backend.controller;

import com.ssafy.backend.model.Data;
import com.ssafy.backend.model.User;
import com.ssafy.backend.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// Sample
@RestController
public class SampleController {

  @Autowired
  private UserRepository repository;

  @ApiOperation(value = "this is delete all user", notes = "모든 사용제 제거하기")
  @GetMapping("/delete")
  public String deleteAll() {
    repository.deleteAll();
    return "delete ok";
  }

  @ApiOperation(value = "Insert test", notes = "입력 테스트")
  @GetMapping("/insert")
  public String insert() {
    List<Data> dataList = new ArrayList<Data>();
    dataList.add(new Data(1, "test", "test"));
    dataList.add(new Data(2, "test2", "test2"));
    dataList.add(new Data(3, "test3", "test3"));

    repository.insert(new User(1, "test", "test@test.com", "test", dataList));
    repository.insert(new User(2, "test2", "test@test.com", "test2", dataList));

    return "insert ok";
  }

  @ApiOperation(value = "Find test", notes = "검색 테스트")
  @GetMapping("/search")
  public List<User> search() {
    try {
      List<User> userList = repository.findAllByEmail("test@test.com");
      return userList;
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @ApiOperation(value = "this is signin", notes = "사용자 가져오기")
  @GetMapping(value = "/login")
  public User emailLogin(@RequestParam String email) {
    System.out.println(email);

    User user = repository.findByEmail(email);

    System.out.println(user);

    return user;
  }

  @ApiOperation(value = "this is signup", notes = "사용자 저장")
  @PostMapping(value = "/login")
  public User gogoLogin(@RequestBody User user) {

    return user;
  }
}