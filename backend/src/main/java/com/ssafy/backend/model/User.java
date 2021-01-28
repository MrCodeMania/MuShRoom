package com.ssafy.backend.model;

import java.util.List;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;

    //회원정보(필수)
    private String email;
    private String password;
    private String phone;
    private String nickname;

    private int point;

    //회원정보(선택)
    private String profile;
    private String introduction;
    private List<UserInstrument> instrument;

    //마이페이지
    private UserLike like;
    private UserClass userClass;
    private List<UserLectureProgress> lectureProgress;
    private List<UserFeedback> feedback;

}
