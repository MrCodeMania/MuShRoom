package com.ssafy.backend.model;

import javax.persistence.GeneratedValue;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "tutor")
public class TutorEntity {

  @MongoId
  @GeneratedValue
  private ObjectId id;
}
