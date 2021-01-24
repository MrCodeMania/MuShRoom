package com.ssafy.backend.model;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefaultTimeStamp {

  private Date createdAt;
  private Date updatedAt;

  public void setCreatedAt(Date date) {
    this.createdAt = date;
  }

  public void setUpdatedAt(Date date) {
    this.updatedAt = date;
  }
}