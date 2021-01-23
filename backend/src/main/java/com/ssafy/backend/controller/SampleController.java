package com.ssafy.backend.controller;

import com.ssafy.backend.model.ClassEntity;
import com.ssafy.backend.model.ClassQuestion;
import com.ssafy.backend.model.ClassReview;
import com.ssafy.backend.model.InstrumentEntity;
import com.ssafy.backend.model.Lecture;
import com.ssafy.backend.model.LectureQuestion;
import com.ssafy.backend.model.Profile;
import com.ssafy.backend.model.Section;
import com.ssafy.backend.model.Tag;
import com.ssafy.backend.model.TutorEntity;
import com.ssafy.backend.repository.ClassRepository;
import com.ssafy.backend.repository.InstrumentRepository;
import com.ssafy.backend.repository.TutorRepository;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

// Sample
@RestController
public class SampleController {

  @Autowired
  private ClassRepository classRepository;
  @Autowired
  private TutorRepository tutorRepository;
  @Autowired
  private InstrumentRepository instrumentRepository;

  @ApiOperation(value = "Get Test", notes = "get all class")
  @GetMapping("/class")
  public List<ClassEntity> getAllClass() {
    try {
      List<ClassEntity> classList = classRepository.findAll();
      return classList;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  @ApiOperation(value = "Post Test", notes = "insert class")
  @PostMapping("/class")
  public ClassEntity insertClassTest() {
    tutorRepository.deleteAll();
    classRepository.deleteAll();
    instrumentRepository.deleteAll();

    TutorEntity tutor = new TutorEntity();

    ClassEntity cls = ClassEntity.builder()
        .profile(new Profile("testPath", "testIntro"))
        .title("testTile")
        .tutor(tutor)
        .build();

    cls.appendReview(new ClassReview(5, "testReview1"));
    cls.appendReview(new ClassReview(4, "testReview2"));

    cls.appendQuestion(new ClassQuestion("testQuestion1"));
    cls.appendQuestion(new ClassQuestion("testQuestion2"));

    Lecture lecture1 = new Lecture("videoPath1");
    Lecture lecture2 = new Lecture("videoPath2");
    Lecture lecture3 = new Lecture("videoPath3");
    Lecture lecture4 = new Lecture("videoPath4");

    Section section1 = new Section("sectionName1");
    section1.appendLecture(lecture1);
    section1.appendLecture(lecture2);
    lecture1.appendQuestion(new LectureQuestion("question1"));
    lecture2.appendQuestion(new LectureQuestion("question2"));

    Section section2 = new Section("sectionName1");
    section2.appendLecture(lecture3);
    section2.appendLecture(lecture4);
    lecture3.appendQuestion(new LectureQuestion("question3"));
    lecture4.appendQuestion(new LectureQuestion("question4"));

    InstrumentEntity instrument1 = new InstrumentEntity("inst1");
    InstrumentEntity instrument2 = new InstrumentEntity("inst2");
    Tag tag1 = new Tag(instrument1, 1);
    Tag tag2 = new Tag(instrument2, 2);
    cls.appendTag(tag1);
    cls.appendTag(tag2);

    cls.appendSection(section1);
    cls.appendSection(section2);

    instrumentRepository.save(instrument1);
    instrumentRepository.save(instrument2);
    tutorRepository.save(tutor);
    classRepository.save(cls);


    return cls;
  }
}