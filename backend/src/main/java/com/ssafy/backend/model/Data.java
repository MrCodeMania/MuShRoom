package com.ssafy.backend.model;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;

@lombok.Data
@AllArgsConstructor
public class Data {
  @Id private Integer id;
  private String type;
  private String value;
}
