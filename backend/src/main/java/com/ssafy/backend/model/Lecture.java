package com.ssafy.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.other.CustomObjectIdSerializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Lecture {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  @NonNull
  private String videoPath;
  private List<LectureQuestion> questionList = new ArrayList<LectureQuestion>();

  public Lecture(String videoPath) {
    this.id = new ObjectId();
    this.videoPath = videoPath;
  }

  public void appendQuestion(LectureQuestion question) {
    questionList.add(question);
  }
}
