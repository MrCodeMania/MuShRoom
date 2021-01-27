package com.ssafy.backend.model;

import lombok.Data;

@Data
public class ErrorMessage {
  private final String message;
  private final Integer status;

}
