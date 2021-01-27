package com.ssafy.backend.model;

import lombok.Data;

@Data
public class IndexObject<T> {
  public Integer index;
  public T data;
}
