package com.ssafy.backend.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;
import lombok.Data;

@Data
@NoArgsConstructor
public class TutorCareer {

  @MongoId
  private ObjectId careerId;

  @NonNull
  private String name;

  private String description;
  private String filePath;

  public TutorCareer(String name, String description, String filePath)
  {
    this.name = name; this.description = description; this.filePath = filePath;
  }
}