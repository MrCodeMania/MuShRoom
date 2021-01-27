package com.ssafy.backend.model;

import java.util.Date;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
public class DefaultTimeStamp {

  @CreatedDate
  private Date createdAt;
  @LastModifiedDate
  private Date updatedAt;
}