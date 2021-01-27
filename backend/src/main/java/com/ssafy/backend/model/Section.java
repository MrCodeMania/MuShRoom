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
public class Section {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  private String sectionName;
  private List<Lecture> lectureList = new ArrayList<Lecture>();

  public Section() {
    this.id = new ObjectId();
  }

  public Section(String sectionName) {
    this.id = new ObjectId();
    this.sectionName = sectionName;
  }
  public void appendLecture(Lecture lecture) {
    lectureList.add(lecture);
  }
}
