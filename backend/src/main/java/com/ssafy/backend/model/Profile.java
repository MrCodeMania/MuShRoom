package com.ssafy.backend.model;

import lombok.NonNull;
import lombok.Data;

@Data
public class Profile {

  @NonNull
  private String imagePath;
  @NonNull
  private String intro;
}
