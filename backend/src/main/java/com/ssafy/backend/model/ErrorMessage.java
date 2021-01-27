package com.ssafy.backend.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
  private final String message;
  private final Integer status;

}
