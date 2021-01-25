package com.ssafy.backend.model;

import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //회원정보
    private UserProfile profile;
    private String email;
    private String password;
    private String phone;
    private String nickname;

    private int point;

    //마이페이지
    private UserLike like;
    private List<UserInstrument> instrument;
    private UserClass userClass;
    private List<UserLectureProgress> lectureProgress;
    private List<UserFeedback> feedback;

    public UserDto(Integer id, UserProfile profile, String email, String password, String phone, String nickname, int point) {
        this.id = id;
        this.profile = profile;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.nickname = nickname;
        this.point = point;
    }

}
