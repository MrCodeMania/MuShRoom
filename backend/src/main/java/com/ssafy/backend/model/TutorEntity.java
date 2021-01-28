package com.ssafy.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.util.CustomObjectIdSerializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@JsonIdentityInfo(generator = IntSequenceGenerator.class, property = "identity") // 순환 참조 방지
@Data
@Document(collection = "tutor")
public class TutorEntity {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId
  private ObjectId id;
  @DBRef
  private List<ClassEntity> classList = new ArrayList<>();
  private String data;

  public void appendClass(ClassEntity cls) {
    classList.add(cls);
  }
}
