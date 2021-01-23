package com.ssafy.backend.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
public class Tag {

  @DBRef
  @NonNull
  private InstrumentEntity instrument;
  @NonNull
  private Integer level;
}
