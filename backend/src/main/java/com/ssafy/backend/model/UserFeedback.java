package com.ssafy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class UserFeedback {
    private int classId;
    private int sectionId;
    private Timestamp datetime;
    private String fileDir;
}
