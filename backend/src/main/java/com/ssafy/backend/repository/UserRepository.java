package com.ssafy.backend.repository;

import com.ssafy.backend.model.User;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
  public User findByEmail(String email);
  public User findByNickname(String nickname);
  public List<User> findAllByEmail(String email);
}