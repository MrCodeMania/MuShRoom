package com.ssafy.backend.other;

public enum ErrorType {
  CLASS_NOT_EXIST(1, "클래스가 존재하지 않습니다."),
  TUTOR_NOT_EXIST(2, "튜터가 존재하지 않습니다."),
  SECTION_NOT_EXIST(3, "섹션이 존재하지 않습니다."),
  USER_NOT_EXIST(4, "사용자가 존재하지 않습니다."),
  LECTURE_NOT_EXIST(5, "강의가 존재하지 않습니다.");

  private final int value;
  private final String text;

  private ErrorType(int value, String text) {
    this.value = value;
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }

  public int value() {
    return value;
  }
}
