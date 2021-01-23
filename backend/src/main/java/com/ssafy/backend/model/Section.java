package com.ssafy.backend.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Section {

  @MongoId
  private ObjectId id;
  @NonNull
  private String sectionName;
  private List<Lecture> lectureList = new ArrayList<Lecture>();

  public Section(String sectionName) {
    this.id = new ObjectId();
    this.sectionName = sectionName;
  }
  public void appendLecture(Lecture lecture) {
    lectureList.add(lecture);
  }
}
