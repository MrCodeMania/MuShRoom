package com.ssafy.backend.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import org.springframework.data.mongodb.core.mapping.MongoId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tutor")
public class Tutor {

  // 인적사항, 로그인 아이디, 비밀번호
  @MongoId
  private ObjectId id;
  @NonNull
  private String password;
  @NonNull
  private String email;
  @NonNull
  private String phoneNumber;
  @NonNull
  private String nickname;
  @NonNull
  private String name;
  // 프로필
  @NonNull
  private Profile tutorProfile;
  // 선생의 포인트
  @NonNull
  @Builder.Default
  private int point = 0;
  // 선생의 좋아요 수
  @NonNull
  @Builder.Default
  private int like = 0;

  // 가르치는 악기 목록
  @NonNull
  @Builder.Default
  private List<ObjectId> instList = new ArrayList<>();

  // 경력 목록
  @Builder.Default
  private List<TutorCareer> tutorCareerList = new ArrayList<>();
  // 수상 목록
  @Builder.Default
  private List<TutorAward> tutorAwardList = new ArrayList<>();
  // 계좌 번호
  @NonNull
  @Builder.Default
  private TutorAccount tutorAccount = new TutorAccount();

  // 강의 중, 혹은 강의한 목록
  @Builder.Default
  private List<TutorClass> tutorClass;
  // 피드백 가능 시간
  @Builder.Default
  private List<TutorFeedbackAvailableTime> tutorFeedbackAvailableTime = new ArrayList<>();
  // 피드백 예약 시간
  @Builder.Default
  private List<TutorFeedbackReservationTime> tutorFeedbackReservationTime = new ArrayList<>();

  public Tutor(){

  }

  // 회원가입 시, 기본 생성자
  public Tutor(Tutor newTutor) {
    this.id = newTutor.getId();
    this.password = newTutor.getPassword();
    this.email = newTutor.getEmail();
    this.phoneNumber = newTutor.getPhoneNumber();
    this.nickname = newTutor.getNickname();
    this.name = newTutor.getName();
    this.tutorProfile = newTutor.getTutorProfile();
    this.instList = newTutor.getInstList();
  }

  // 기본적으로 get, set은 구현이 되어있음.


}
