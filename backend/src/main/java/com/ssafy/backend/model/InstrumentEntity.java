package com.ssafy.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.util.CustomObjectIdSerializer;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "instrument")
public class InstrumentEntity {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  @NonNull
  private String instrumentName;

  public InstrumentEntity(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  public InstrumentEntity() {}

  // Temp
}
