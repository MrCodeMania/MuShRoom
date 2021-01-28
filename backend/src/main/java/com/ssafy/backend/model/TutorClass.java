package com.ssafy.backend.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
public class TutorClass {
//  @DBRef
//  @Builder.Default
//  private List<ClassEntity> teachingClass = new ArrayList<ClassEntity>(); // 가르치고 있는 강의 목록

//  @DBRef
//  @Builder.Default
//  private List<ClassEntity> taughtClass = new ArrayList<ClassEntity>(); // 가르쳤던 강의 목록
}