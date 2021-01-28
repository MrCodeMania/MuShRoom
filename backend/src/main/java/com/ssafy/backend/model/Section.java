package com.ssafy.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.util.CustomObjectIdSerializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "section")
public class Section {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  private String sectionName;
  @DBRef
  private List<Lecture> lectureList = new ArrayList<>();

  public Section(String sectionName) {
    this.sectionName = sectionName;
  }

  public Section() {}

  public void appendLecture(Lecture lecture) {
    lectureList.add(lecture);
  }
}
