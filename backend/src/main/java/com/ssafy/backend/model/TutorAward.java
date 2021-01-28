package com.ssafy.backend.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;
import lombok.Data;

@Data
@NoArgsConstructor
public class TutorAward {

  @MongoId
  private ObjectId awardId;

  @NonNull
  private String name;

  private String description;

  private String filePath;
}