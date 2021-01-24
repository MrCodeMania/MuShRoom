package com.ssafy.backend.controller;


import com.ssafy.backend.model.ClassEntity;
import com.ssafy.backend.model.ClassQuestion;
import com.ssafy.backend.model.ClassReview;
import com.ssafy.backend.model.DefaultTimeStamp;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {

  @Autowired
  private ClassRepository classRepository;
  @Autowired
  private TutorRepository tutorRepository;
  @Autowired
  private InstrumentRepository instrumentRepository;

  @ApiOperation(value = "Get All Class List", notes = "모든 클래스의 리스트를 반환한다")
  @GetMapping("/class")
  public ResponseEntity<List<ClassEntity>> getAllClass() {
    List<ClassEntity> classList = classRepository.findAll();
    return new ResponseEntity<List<ClassEntity>>(classList, HttpStatus.OK);
  }

  @ApiOperation(value = "Get Class", notes = "ObjectID로 부터 클래스를 가져와 반환한다")
  @GetMapping("/class/{id}")
  public ClassEntity getOneClass(@PathVariable ObjectId id) {
    System.out.println(id.toString());
    ClassEntity cls = classRepository.findById(id).orElseThrow(
        new Supplier<IllegalArgumentException>() {
          @Override
          public IllegalArgumentException get() {
            return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
          }
        });

    /* 람다 표현식
    ClassEntity cls = classRepository.findById(id.toString()).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    }); */

    return cls;
  }

  @ApiOperation(value = "Update Class", notes = "ObjectID로 부터 클래스를 변경한다")
  @PutMapping("/class/{id}")
  public ClassEntity updateOneClass(@PathVariable ObjectId id, @RequestBody ClassEntity cls) {
    return null;
  }

  // insert test sample
  @ApiOperation(value = "Post New Class", notes = "새로운 클래스를 삽입한다")
  @PostMapping("/class")
  public ClassEntity insertClassTest() {
    tutorRepository.deleteAll();
    classRepository.deleteAll();
    instrumentRepository.deleteAll();

    TutorEntity tutor = new TutorEntity();

    Date now = new Date();
    ClassEntity cls = ClassEntity.builder()
        .profile(new Profile("testPath", "testIntro"))
        .title("testTile")
        .tutor(tutor)
        .timeStamp(DefaultTimeStamp.builder().createdAt(now).updatedAt(now).build())
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