package com.ssafy.backend.controller;

import com.ssafy.backend.model.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


// Sample
@RestController
public class SampleController {

  @ApiOperation(value = "this is signin", notes = "사용자 가져오기")
  @GetMapping(value = "/login")
  public ResponseEntity gogoLogin(@RequestParam Integer id) {
    return ResponseEntity.ok(id);
  }

  @ApiOperation(value = "this is signup", notes = "사용자 저장")
  @PostMapping(value = "/login")
  public ResponseEntity gogoLogin(@RequestBody User user) {
    return ResponseEntity.ok(user);
  }
}