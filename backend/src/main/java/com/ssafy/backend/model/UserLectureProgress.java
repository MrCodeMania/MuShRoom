package com.ssafy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLectureProgress {
    private int classId;
    private int sectionId;
    private int lectureId;
    private int progress;
}
