package com.ssafy.backend.model;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "class.tag")
public class Tag {

  @DBRef
  @NonNull
  private InstrumentEntity instrument;
  @NonNull
  private Integer level;
}
