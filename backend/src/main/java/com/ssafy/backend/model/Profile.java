package com.ssafy.backend.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Data;

@Data
@NoArgsConstructor
public class Profile {

  @NonNull
  private String imagePath;
  @NonNull
  private String intro;

  public Profile(String imagePath, String intro){
    this.imagePath = imagePath;
    this.intro = intro;
  }
}
