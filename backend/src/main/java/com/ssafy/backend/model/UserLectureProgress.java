package com.ssafy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLectureProgress {
    private int classId;
    private int sectionId;
    private int lectureId;
    private int progress;
}
