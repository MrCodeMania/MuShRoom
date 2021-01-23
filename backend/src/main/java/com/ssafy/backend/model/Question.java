package com.ssafy.backend.model;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Question {

  @MongoId
  private ObjectId id;
  @NonNull
  private String question = "";
  private String answer;

  public Question(String question) {
    this.id = new ObjectId();
    this.question = question;
  }
}
