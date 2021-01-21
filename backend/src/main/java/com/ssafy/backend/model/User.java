package com.ssafy.backend.model;

import com.sun.istack.NotNull;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
  @Id private Integer id;
  @NotNull private String password;
  @NotNull private String email;
  @NotNull private String nickname;

  public User() {}
  public User(Integer id, String password, String email, String nickname) {
    this.id = id;
    this.password = password;
    this.email = email;
    this.nickname = nickname;
  }
}
