package com.ssafy.backend.model;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TutorFeedbackAvailableTime {
  @NonNull
  private Date dateTime;

  @NonNull
  private boolean reservation;

  public TutorFeedbackAvailableTime(Date dateTime, boolean reservation){
    this.dateTime = dateTime;
    this.reservation = reservation;
  }
}