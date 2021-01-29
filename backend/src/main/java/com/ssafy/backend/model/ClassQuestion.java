package com.ssafy.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "class.question")
public class ClassQuestion extends Question {

  public ClassQuestion(String question) {
    super(question);
  }

  public ClassQuestion() { super(); }
}
