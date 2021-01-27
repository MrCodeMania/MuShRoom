package com.ssafy.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.other.CustomObjectIdSerializer;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "tutor")
public class TutorEntity {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  private String data;
}
