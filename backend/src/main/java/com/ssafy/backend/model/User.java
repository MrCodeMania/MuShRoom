package com.ssafy.backend.model;

import com.sun.istack.NotNull;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String password;
  private String email;
  private String nickname;
  private List<com.ssafy.backend.model.Data> dataList;

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%s, nickname='%s', email='%s']",
        id, nickname, email);
  }
}
