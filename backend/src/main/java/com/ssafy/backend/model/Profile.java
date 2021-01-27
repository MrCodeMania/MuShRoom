package com.ssafy.backend.model;

import lombok.NonNull;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "class.profile")
public class Profile {

  @NonNull
  private String imagePath;
  @NonNull
  private String intro;
}
