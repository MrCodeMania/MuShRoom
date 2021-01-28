package com.ssafy.backend.model;

import java.sql.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TutorFeedbackReservationTime {
  @NonNull
  private int classId;

  @NonNull
  private int sectionId;

  @NonNull
  private Date dateTime;

  @NonNull
  private int userId;
}