package com.ssafy.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.other.CustomObjectIdSerializer;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class ClassReview {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  @NonNull
  private Integer score;
  @NonNull
  private String review;

  public ClassReview(Integer score, String review) {
    this.id = new ObjectId();
    this.score = score;
    this.review = review;
  }
}
