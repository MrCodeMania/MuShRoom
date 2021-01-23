package com.ssafy.backend.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Builder
@Document(collection = "class") // 설정하지 않을 시 자동으로 클래스 이름(첫 글자 소문자)이 collection으로
public class ClassEntity {

  @MongoId // 설정하지 않을 시 _id, id 멤버 변수가 자동으로 _id 필드로 매핑
  private ObjectId id;
  @NonNull
  private Profile profile;
  @NonNull
  private String title;
  @DBRef
  @NonNull
  private TutorEntity tutor;
  @Builder.Default
  private List<ClassReview> reviewList = new ArrayList<ClassReview>();
  @NonNull
  @Builder.Default
  private Integer like = 0;
  @NonNull
  @Builder.Default
  private Integer studentCount = 0;
  @Builder.Default
  private List<ClassQuestion> questionList = new ArrayList<ClassQuestion>();
  @Builder.Default
  private List<Section> sectionList = new ArrayList<Section>();
  @Builder.Default
  private List<Tag> tagList = new ArrayList<Tag>();

  public void appendReview(ClassReview review) {
    reviewList.add(review);
  }

  public void appendQuestion(ClassQuestion question) {
    questionList.add(question);
  }

  public void appendSection(Section section) { sectionList.add(section); }

  public void appendTag(Tag tag) { tagList.add(tag); }
}