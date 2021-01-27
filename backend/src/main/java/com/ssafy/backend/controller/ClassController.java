package com.ssafy.backend.controller;


import com.ssafy.backend.model.ClassEntity;
import com.ssafy.backend.model.ClassQuestion;
import com.ssafy.backend.model.ClassReview;
import com.ssafy.backend.model.DefaultTimeStamp;
import com.ssafy.backend.model.ErrorMessage;
import com.ssafy.backend.model.IndexObject;
import com.ssafy.backend.model.InstrumentEntity;
import com.ssafy.backend.model.Lecture;
import com.ssafy.backend.model.LectureQuestion;
import com.ssafy.backend.model.Profile;
import com.ssafy.backend.model.Section;
import com.ssafy.backend.model.Tag;
import com.ssafy.backend.model.TutorEntity;
import com.ssafy.backend.other.ErrorType;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {

  private final ClassRepository classRepository;
  private final TutorRepository tutorRepository;
  private final InstrumentRepository instrumentRepository;

  @Autowired
  public ClassController(ClassRepository classRepository,
      TutorRepository tutorRepository,
      InstrumentRepository instrumentRepository) {
    this.classRepository = classRepository;
    this.tutorRepository = tutorRepository;
    this.instrumentRepository = instrumentRepository;
  }

  @ApiOperation(value = "Get All Class List", notes = "모든 클래스의 리스트를 반환한다")
  @GetMapping("/class")
  public ResponseEntity<List<ClassEntity>> getAllClass() {
    return new ResponseEntity<>(classRepository.findAll(), HttpStatus.OK);
  }

  @ApiOperation(value = "Insert New Class", notes = "새로운 클래스를 삽입한다")
  @PostMapping("/class")
  public ResponseEntity<?> insertOneClass(@RequestBody TutorEntity t) {

    Optional<TutorEntity> tutor = tutorRepository.findById(t.getId());
    if (tutor.isPresent()) {
      ClassEntity cls = ClassEntity.builder().tutor(tutor.get()).build();
      classRepository.save(cls);
      return new ResponseEntity<>(cls, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get Class", notes = "ObjectID로 부터 클래스를 가져와 반환한다")
  @GetMapping("/class/{cid}")
  public ClassEntity getOneClass(@PathVariable ObjectId cid) {
    return classRepository.findById(cid).orElseThrow(
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
  }

  @ApiOperation(value = "Update Class", notes = "해당 ObjectID의 클래스를 변경한다.\n"
      + "변경 가능 내역은 클래스 프로필, 타이틀, 태그 리스트에 한정한다.\n"
      + "변경하고자 하는 필드가 아닌 경우에는 값을 입력하지 않는다.")
  @PutMapping("/class/{cid}")
  public ClassEntity updateOneClass(@PathVariable ObjectId cid, @RequestBody ClassEntity cls) {
    System.out.println(cls);
    ClassEntity before = classRepository.findById(cid).orElseThrow(() ->
        new IllegalArgumentException("해당 사용자는 존재하지 않습니다")
    );
    if (cls.getProfile() != null) {
      before.setProfile(cls.getProfile());
    }
    if (cls.getTitle() != null) {
      before.setTitle(cls.getTitle());
    }
    if (cls.getTagList() != null) {
      before.setTagList(cls.getTagList());
    }

    classRepository.save(before);

    return before;
  }

  @ApiOperation(value = "Delete Class", notes = "해당 ObjectID의 클래스를 제거한다.")
  @DeleteMapping("/class/{cid}")
  public ResponseEntity<ClassEntity> deleteOneClass(@PathVariable ObjectId cid) {

    Optional<ClassEntity> cls = classRepository.findById(cid);
    if (cls.isPresent()) {
      classRepository.deleteById(cid);
      return new ResponseEntity<>(cls.get(), HttpStatus.OK);
    }

    return new ResponseEntity(
        new ErrorMessage(ErrorType.CLASS_NOT_EXIST.toString(), HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get All Section", notes = "ObjectID로 부터 클래스를 가져와 세션을 반환한다")
  @GetMapping("/class/{cid}/section")
  public ResponseEntity<?> getAllSection(@PathVariable ObjectId cid) {
    Optional<ClassEntity> cls = classRepository.findById(cid);
    if (cls.isPresent()) {
      List<Section> sectionList = cls.get().getSectionList();
      return new ResponseEntity<>(sectionList, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Update Section", notes = "ObjectID로 부터 클래스를 가져오며, index에 Section을 삽입한다")
  @PostMapping("/class/{cid}/section")
  public ResponseEntity<?> insertOneSection(
      @PathVariable ObjectId cid,
      @RequestBody IndexObject<Section> data) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<Section> sectionList = cls.getSectionList();

      // 사이즈보다 요청 인덱스가 클 경우 오류 제어
      if (sectionList.size() < data.index) {
        data.index = sectionList.size();
      }
      if (data.index < 0) {
        data.index = 0;
      }

      sectionList.add(data.index, data.data);
      cls.setSectionList(sectionList);
      classRepository.save(cls);

      return new ResponseEntity<>(sectionList, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get One Section", notes = "ObjectId로 부터 클래스를 가져오며, ObjectId로 부터 섹션을 가져온다.")
  @GetMapping("/class/{cid}/section/{sid}")
  public ResponseEntity<?> getOneSection(@PathVariable ObjectId cid, @PathVariable ObjectId sid) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<Section> sectionList = cls.getSectionList();
      for (Section section : sectionList) {
        if (section.getId().equals(sid)) {
          return new ResponseEntity<>(section, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.SECTION_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Update Section", notes = "ObjectID로 부터 클래스를 가져오며, ObjectId로 부터 섹션을 가져와 수정한다.")
  @PutMapping("/class/{cid}/section/{sid}")
  public ResponseEntity<?> updateOneSection(
      @PathVariable ObjectId cid,
      @PathVariable ObjectId sid,
      @RequestBody Section section) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<Section> sectionList = cls.getSectionList();

      for (int i = 0; i < sectionList.size(); i++) {
        if (sectionList.get(i).getId().equals(sid)) {
          Section before = sectionList.get(i);
          if (section.getSectionName() != null) {
            before.setSectionName(section.getSectionName());
          }
          if (section.getLectureList() != null) {
            before.setLectureList(section.getLectureList());
          }

          sectionList.set(i, before);
          cls.setSectionList(sectionList);
          classRepository.save(cls);

          return new ResponseEntity<>(before, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.SECTION_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Delete Section", notes = "ObjectID로 부터 클래스를 가져오며, ObjectId와 일치하는 섹션을 제거한다.")
  @DeleteMapping("/class/{cid}/section/{sid}")
  public ResponseEntity<?> deleteOneSection(@PathVariable ObjectId cid,
      @PathVariable ObjectId sid) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<Section> sectionList = cls.getSectionList();

      int index = -1;
      for (int i = 0; i < sectionList.size(); i++) {
        if (sectionList.get(i).getId().equals(sid)) {
          index = i;
          break;
        }
      }

      if (index >= 0) {
        Section rs = sectionList.get(index);
        sectionList.remove(index);
        cls.setSectionList(sectionList);
        classRepository.save(cls);

        return new ResponseEntity<>(rs, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            new ErrorMessage(
                ErrorType.SECTION_NOT_EXIST.toString(),
                HttpStatus.BAD_REQUEST.value()),
            HttpStatus.BAD_REQUEST);
      }
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  // insert test sample
  @ApiOperation(value = "Post New Class", notes = "새로운 클래스를 삽입한다")
  @PostMapping("/test")
  public ClassEntity insertClassTest() {
    tutorRepository.deleteAll();
    classRepository.deleteAll();
    instrumentRepository.deleteAll();

    TutorEntity tutor = new TutorEntity();
    tutor.setData("testData");

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