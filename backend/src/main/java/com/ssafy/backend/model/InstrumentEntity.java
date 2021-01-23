package com.ssafy.backend.model;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "instrument")
public class InstrumentEntity {

  @MongoId
  private ObjectId id;
  @NonNull
  private String instrumentName;
  public InstrumentEntity(String instrumentName) {
    this.id = new ObjectId();
    this.instrumentName = instrumentName;
  }

  // Temp
}
