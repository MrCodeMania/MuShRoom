package com.ssafy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class ProfileIntro {
  @NonNull
  public String subtitle;
  @NonNull
  public String description;
  public String imagePath;
}
