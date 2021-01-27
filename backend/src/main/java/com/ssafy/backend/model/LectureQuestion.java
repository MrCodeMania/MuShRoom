package com.ssafy.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lecture.question")
public class LectureQuestion extends Question {

  public LectureQuestion(String question) {
    super(question);
  }
}
