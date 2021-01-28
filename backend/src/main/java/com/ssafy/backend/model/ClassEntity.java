package com.ssafy.backend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ssafy.backend.util.CustomObjectIdSerializer;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@JsonIdentityInfo(generator = IntSequenceGenerator.class, property = "identity") // 순환 참조 방지
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@Document(collection = "class") // 설정하지 않을 시 자동으로 클래스 이름(첫 글자 소문자)이 collection으로
public class ClassEntity extends DefaultTimeStamp {

  @JsonSerialize(using = CustomObjectIdSerializer.class)
  @MongoId // 설정하지 않을 시 _id, id 멤버 변수가 자동으로 _id 필드로 매핑
  private ObjectId id;
  private Profile profile;
  private String title;
  @DBRef
  private TutorEntity tutor;
  @Builder.Default
  @DBRef
  private List<ClassReview> reviewList = new ArrayList<>();
  @Builder.Default
  private Integer like = 0;
  @Builder.Default
  private Integer studentCount = 0;
  @Builder.Default
  @DBRef
  private List<ClassQuestion> questionList = new ArrayList<>();
  @Builder.Default
  @DBRef
  private List<Section> sectionList = new ArrayList<>();
  @Builder.Default
  private List<Tag> tagList = new ArrayList<>();

  public void appendReview(ClassReview review) {
    reviewList.add(review);
  }

  public void appendQuestion(ClassQuestion question) {
    questionList.add(question);
  }

  public void appendSection(Section section) { sectionList.add(section); }

  public void appendTag(Tag tag) { tagList.add(tag); }
}