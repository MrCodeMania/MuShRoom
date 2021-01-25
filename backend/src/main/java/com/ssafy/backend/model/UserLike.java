package com.ssafy.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLike {
    private int[] classId;
    private int[] tutorId;
}
