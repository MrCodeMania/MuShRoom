package com.ssafy.backend.controller;


import com.ssafy.backend.model.ClassEntity;
import com.ssafy.backend.model.ClassQuestion;
import com.ssafy.backend.model.ClassReview;
import com.ssafy.backend.model.ErrorMessage;
import com.ssafy.backend.model.InstrumentEntity;
import com.ssafy.backend.model.Lecture;
import com.ssafy.backend.model.LectureQuestion;
import com.ssafy.backend.model.Profile;
import com.ssafy.backend.model.Section;
import com.ssafy.backend.model.Tag;
import com.ssafy.backend.model.Tutor;
import com.ssafy.backend.util.ErrorType;
import com.ssafy.backend.repository.ClassQuestionRepository;
import com.ssafy.backend.repository.ClassRepository;
import com.ssafy.backend.repository.ClassReviewRepository;
import com.ssafy.backend.repository.InstrumentRepository;
import com.ssafy.backend.repository.LectureQuestionRepository;
import com.ssafy.backend.repository.LectureRepository;
import com.ssafy.backend.repository.SectionRepository;
import com.ssafy.backend.repository.TutorRepository;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClassController {

  private final ClassQuestionRepository classQuestionRepository;
  private final ClassRepository classRepository;
  private final ClassReviewRepository classReviewRepository;
  private final InstrumentRepository instrumentRepository;
  private final LectureQuestionRepository lectureQuestionRepository;
  private final LectureRepository lectureRepository;
  private final SectionRepository sectionRepository;
  private final TutorRepository tutorRepository;


  // Repository DI
  @Autowired
  public ClassController(
      ClassQuestionRepository classQuestionRepository,
      ClassRepository classRepository,
      ClassReviewRepository classReviewRepository,
      InstrumentRepository instrumentRepository,
      LectureQuestionRepository lectureQuestionRepository,
      LectureRepository lectureRepository,
      SectionRepository sectionRepository,
      TutorRepository tutorRepository) {
    this.classQuestionRepository = classQuestionRepository;
    this.classRepository = classRepository;
    this.classReviewRepository = classReviewRepository;
    this.instrumentRepository = instrumentRepository;
    this.lectureQuestionRepository = lectureQuestionRepository;
    this.lectureRepository = lectureRepository;
    this.sectionRepository = sectionRepository;
    this.tutorRepository = tutorRepository;
  }

  @ApiOperation(value = "Get All Class List", notes = "모든 클래스의 리스트를 반환한다")
  @GetMapping("/class")
  public ResponseEntity<List<ClassEntity>> getAllClass() {
    return new ResponseEntity<>(classRepository.findAll(), HttpStatus.OK);
  }

  @ApiOperation(value = "Insert New Class", notes = "새로운 클래스를 삽입한다")
  @PostMapping("/class")
  public ResponseEntity<?> insertOneClass(
      @RequestParam ObjectId tutorId,
      @RequestBody ClassEntity c) {

    Optional<Tutor> tutor = tutorRepository.findById(tutorId);
    if (tutor.isPresent()) {
      ClassEntity cls = ClassEntity.builder()
          .tutor(tutor.get())
          .title(c.getTitle())
          .profile(c.getProfile())
          .build();
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
    return classRepository.findById(cid).orElseThrow(() ->
      new IllegalArgumentException("해당 사용자는 존재하지 않습니다")
    );
  }

  @ApiOperation(value = "Update Class", notes = "해당 ObjectID의 클래스를 변경한다.\n"
      + "변경 가능 내역은 클래스 프로필, 타이틀, 태그 리스트에 한정한다.\n"
      + "변경하고자 하는 필드가 아닌 경우에는 값을 입력하지 않는다.")
  @PutMapping("/class/{cid}")
  public ClassEntity updateOneClass(@PathVariable ObjectId cid, @RequestBody ClassEntity cls) {
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
  @GetMapping("/section")
  public ResponseEntity<?> getAllSection(@RequestParam(name = "classId") ObjectId cid) {
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

  @ApiOperation(value = "Insert Section", notes = "ObjectID로 부터 클래스를 가져오며, index에 Section을 삽입한다")
  @PostMapping("/section")
  public ResponseEntity<?> insertOneSection(
      @RequestParam(name = "classId") ObjectId cid,
      @RequestParam(required = false) Integer index,
      @RequestBody Section section) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<Section> sectionList = cls.getSectionList();
      // sectionId를 store 하기 위한 save
      sectionRepository.save(section);

      if (index != null) {
        // 사이즈보다 요청 인덱스가 클 경우 오류 제어
        if (sectionList.size() < index) {
          index = sectionList.size();
        }
        if (index < 0) {
          index = 0;
        }
        sectionList.add(index, section);
      } else {
        sectionList.add(section);
      }

      cls.setSectionList(sectionList);
      classRepository.save(cls);

      return new ResponseEntity<>(section, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get One Section", notes = "ObjectId로 부터 클래스를 가져오며, ObjectId로 부터 섹션을 가져온다.")
  @GetMapping("/section/{sid}")
  public ResponseEntity<?> getOneSection(
      @RequestParam(name = "classId") ObjectId cid,
      @PathVariable ObjectId sid) {
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
  @PutMapping("/section/{sid}")
  public ResponseEntity<?> updateOneSection(
      @RequestParam(name = "classId") ObjectId cid,
      @PathVariable ObjectId sid,
      @RequestBody Section section) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<Section> sectionList = cls.getSectionList();

      for (Section tmp : sectionList) {
        if (tmp.getId().equals(sid)) {
          Section s = sectionRepository.findById(sid).get();
          s.setSectionName(section.getSectionName());
          sectionRepository.save(s);

          return new ResponseEntity<>(s, HttpStatus.OK);
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
  @DeleteMapping("/section/{sid}")
  public ResponseEntity<?> deleteOneSection(
      @RequestParam(name = "classId") ObjectId cid,
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
        sectionRepository.delete(rs);

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

  @ApiOperation(value = "Get All Lecture", notes = "모든 Lecture를 가져온다.")
  @GetMapping("/lecture")
  public ResponseEntity<?> getAllLecture(@RequestParam(name = "sectionId") ObjectId sid) {
    Optional<Section> sectionOpt = sectionRepository.findById(sid);

    if (sectionOpt.isPresent()) {
      return new ResponseEntity<>(sectionOpt.get().getLectureList(), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.SECTION_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Insert Lecture", notes = "Lecture를 삽입한다.")
  @PostMapping("/lecture")
  public ResponseEntity<?> insertOneLecture(
      @RequestParam(name = "sectionId") ObjectId sid,
      @RequestParam(required = false) Integer index,
      @RequestBody Lecture lecture) {
    Optional<Section> sectionOpt = sectionRepository.findById(sid);
    if (sectionOpt.isPresent()) {
      Section section = sectionOpt.get();
      List<Lecture> lectureList = section.getLectureList();
      lectureRepository.save(lecture);

      if (index != null) {
        if (lectureList.size() < index) {
          index = lectureList.size();
        }
        if (index < 0) {
          index = 0;
        }
        lectureList.add(index, lecture);
      } else {
        lectureList.add(lecture);
      }

      section.setLectureList(lectureList);
      sectionRepository.save(section);

      return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.SECTION_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get One Lecture", notes = "Lecture를 리턴한다.")
  @GetMapping("/lecture/{lid}")
  public ResponseEntity<?> getOneLecture(
      @RequestParam(name = "sectionId") ObjectId sid,
      @PathVariable ObjectId lid) {
    Optional<Section> sectionOpt = sectionRepository.findById(sid);
    if (sectionOpt.isPresent()) {
      Section section = sectionOpt.get();
      List<Lecture> lectureList = section.getLectureList();
      for (Lecture lecture : lectureList) {
        if (lecture.getId().equals(lid)) {
          return new ResponseEntity<>(lecture, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.LECTURE_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.SECTION_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Update Lecture", notes = "강의를 수정한다.")
  @PutMapping("/lecture/{lid}")
  public ResponseEntity<?> updateOneLecture(
      @RequestParam(name = "sectionId") ObjectId sid,
      @PathVariable ObjectId lid,
      @RequestBody Lecture lecture) {
    Optional<Section> sectionOpt = sectionRepository.findById(sid);
    if (sectionOpt.isPresent()) {
      Section section = sectionOpt.get();
      List<Lecture> lectureList = section.getLectureList();

      for (Lecture tmp : lectureList) {
        if (tmp.getId().equals(lid)) {
          Lecture l = lectureRepository.findById(lid).get();
          l.setVideoPath(lecture.getVideoPath());
          lectureRepository.save(l);

          return new ResponseEntity<>(l, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.LECTURE_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.SECTION_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Delete Lecture", notes = "강의를 제거한다.")
  @DeleteMapping("/lecture/{lid}")
  public ResponseEntity<?> deleteOneLecture(
      @RequestParam(name = "sectionId") ObjectId sid,
      @PathVariable ObjectId lid) {
    Optional<Section> sectionOpt = sectionRepository.findById(sid);
    if (sectionOpt.isPresent()) {
      Section section = sectionOpt.get();
      List<Lecture> lectureList = section.getLectureList();

      int index = -1;
      for (int i = 0; i < lectureList.size(); i++) {
        if (lectureList.get(i).getId().equals(lid)) {
          index = i;
          break;
        }
      }

      if (index >= 0) {
        Lecture rs = lectureList.get(index);
        lectureList.remove(index);
        section.setLectureList(lectureList);
        sectionRepository.save(section);
        lectureRepository.delete(rs);

        return new ResponseEntity<>(rs, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            new ErrorMessage(
                ErrorType.LECTURE_NOT_EXIST.toString(),
                HttpStatus.BAD_REQUEST.value()),
            HttpStatus.BAD_REQUEST);
      }
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.SECTION_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get All Class Question", notes = "모든 클래스의 질문을 가져온다.")
  @GetMapping("/class-question")
  public ResponseEntity<?> getAllClassQuestion(@RequestParam(name = "classId") ObjectId cid) {
    Optional<ClassEntity> classOpt = classRepository.findById(cid);

    if (classOpt.isPresent()) {
      return new ResponseEntity<>(classOpt.get().getQuestionList(), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Insert Class Question", notes = "클래스 질문을 삽입한다.")
  @PostMapping("/class-question")
  public ResponseEntity<?> insertOneClassQuestion(
      @RequestParam(name = "classId") ObjectId cid,
      @RequestBody ClassQuestion classQuestion) {
    Optional<ClassEntity> classOpt = classRepository.findById(cid);
    if (classOpt.isPresent()) {
      ClassEntity cls = classOpt.get();
      classQuestionRepository.save(classQuestion);

      cls.appendQuestion(classQuestion);
      classRepository.save(cls);

      return new ResponseEntity<>(classQuestion, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get One Class Question", notes = "클래스 질문을 리턴한다.")
  @GetMapping("/class-question/{qid}")
  public ResponseEntity<?> getOneClassQuestion(
      @RequestParam(name = "classId") ObjectId cid,
      @PathVariable ObjectId qid) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<ClassQuestion> classQuestionList = cls.getQuestionList();
      for (ClassQuestion classQuestion : classQuestionList) {
        if (classQuestion.getId().equals(qid)) {
          return new ResponseEntity<>(classQuestion, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.CLASS_QUESTION_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Update Class Question", notes = "클래스 질문을 수정한다.")
  @PutMapping("/class-question/{qid}")
  public ResponseEntity<?> updateOneLecture(
      @RequestParam(name = "classId") ObjectId cid,
      @PathVariable ObjectId qid,
      @RequestBody ClassQuestion classQuestion) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<ClassQuestion> classQuestionList = cls.getQuestionList();

      for (ClassQuestion tmp : classQuestionList) {
        if (tmp.getId().equals(qid)) {
          ClassQuestion q = classQuestionRepository.findById(qid).get();
          if (classQuestion.getQuestion() != null) q.setQuestion(classQuestion.getQuestion());
          if (classQuestion.getAnswer() != null) q.setAnswer(classQuestion.getAnswer());
          classQuestionRepository.save(q);

          return new ResponseEntity<>(q, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.CLASS_QUESTION_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.CLASS_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Delete Class Question", notes = "클래스 질문을 제거한다.")
  @DeleteMapping("/class-question/{qid}")
  public ResponseEntity<?> deleteOneClassQuestion(
      @RequestParam(name = "classId") ObjectId cid,
      @PathVariable ObjectId qid) {
    Optional<ClassEntity> clsOpt = classRepository.findById(cid);
    if (clsOpt.isPresent()) {
      ClassEntity cls = clsOpt.get();
      List<ClassQuestion> classQuestionList = cls.getQuestionList();

      int index = -1;
      for (int i = 0; i < classQuestionList.size(); i++) {
        if (classQuestionList.get(i).getId().equals(qid)) {
          index = i;
          break;
        }
      }

      if (index >= 0) {
        ClassQuestion rs = classQuestionList.get(index);
        classQuestionList.remove(index);
        cls.setQuestionList(classQuestionList);
        classRepository.save(cls);
        classQuestionRepository.delete(rs);

        return new ResponseEntity<>(rs, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            new ErrorMessage(
                ErrorType.CLASS_QUESTION_NOT_EXIST.toString(),
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

  @ApiOperation(value = "Get All Lecture Question", notes = "강의의 질문을 가져온다.")
  @GetMapping("/lecture-question")
  public ResponseEntity<?> getAllLectureQuestion(@RequestParam(name = "lectureId") ObjectId lid) {
    Optional<Lecture> lectureOpt = lectureRepository.findById(lid);

    if (lectureOpt.isPresent()) {
      return new ResponseEntity<>(lectureOpt.get().getQuestionList(), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.LECTURE_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Insert Lecture Question", notes = "강의 질문을 삽입한다.")
  @PostMapping("/lecture-question")
  public ResponseEntity<?> insertOneClassQuestion(
      @RequestParam(name = "lectureId") ObjectId lid,
      @RequestBody LectureQuestion lectureQuestion) {
    Optional<Lecture> lectureOpt = lectureRepository.findById(lid);
    if (lectureOpt.isPresent()) {
      Lecture lecture = lectureOpt.get();
      lectureQuestionRepository.save(lectureQuestion);

      lecture.appendQuestion(lectureQuestion);
      lectureRepository.save(lecture);

      return new ResponseEntity<>(lecture, HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.LECTURE_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Get One Lecture Question", notes = "강의 질문을 리턴한다.")
  @GetMapping("/lecture-question/{qid}")
  public ResponseEntity<?> getOneLectureQuestion(
      @RequestParam(name = "lectureId") ObjectId lid,
      @PathVariable ObjectId qid) {
    Optional<Lecture> lectureOpt = lectureRepository.findById(lid);
    if (lectureOpt.isPresent()) {
      Lecture lecture = lectureOpt.get();
      List<LectureQuestion> lectureQuestionList = lecture.getQuestionList();
      for (LectureQuestion lectureQuestion : lectureQuestionList) {
        if (lectureQuestion.getId().equals(qid)) {
          return new ResponseEntity<>(lectureQuestion, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.LECTURE_QUESTION_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.LECTURE_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Update Lecture Question", notes = "강의 질문을 수정한다.")
  @PutMapping("/lecture-question/{qid}")
  public ResponseEntity<?> updateOneLecture(
      @RequestParam(name = "lectureId") ObjectId lid,
      @PathVariable ObjectId qid,
      @RequestBody LectureQuestion lectureQuestion) {
    Optional<Lecture> lectureOpt = lectureRepository.findById(lid);
    if (lectureOpt.isPresent()) {
      Lecture lecture = lectureOpt.get();
      List<LectureQuestion> lectureQuestionList = lecture.getQuestionList();

      for (LectureQuestion tmp : lectureQuestionList) {
        if (tmp.getId().equals(qid)) {
          LectureQuestion q = lectureQuestionRepository.findById(qid).get();
          if (lectureQuestion.getQuestion() != null) q.setQuestion(lectureQuestion.getQuestion());
          if (lectureQuestion.getAnswer() != null) q.setAnswer(lectureQuestion.getAnswer());
          lectureQuestionRepository.save(q);

          return new ResponseEntity<>(q, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>(
          new ErrorMessage(
              ErrorType.LECTURE_QUESTION_NOT_EXIST.toString(),
              HttpStatus.BAD_REQUEST.value()),
          HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.LECTURE_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  @ApiOperation(value = "Delete Lecture Question", notes = "강의 질문을 제거한다.")
  @DeleteMapping("/lecture-question/{qid}")
  public ResponseEntity<?> deleteOneLectureQuestion(
      @RequestParam(name = "lectureId") ObjectId lid,
      @PathVariable ObjectId qid) {
    Optional<Lecture> lectureOpt = lectureRepository.findById(lid);
    if (lectureOpt.isPresent()) {
      Lecture lecture = lectureOpt.get();
      List<LectureQuestion> lectureQuestionList = lecture.getQuestionList();

      int index = -1;
      for (int i = 0; i < lectureQuestionList.size(); i++) {
        if (lectureQuestionList.get(i).getId().equals(qid)) {
          index = i;
          break;
        }
      }

      if (index >= 0) {
        LectureQuestion rs = lectureQuestionList.get(index);
        lectureQuestionList.remove(index);
        lecture.setQuestionList(lectureQuestionList);
        lectureRepository.save(lecture);
        lectureQuestionRepository.delete(rs);

        return new ResponseEntity<>(rs, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            new ErrorMessage(
                ErrorType.LECTURE_QUESTION_NOT_EXIST.toString(),
                HttpStatus.BAD_REQUEST.value()),
            HttpStatus.BAD_REQUEST);
      }
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.LECTURE_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }

  // insert test sample
  @ApiOperation(value = "Post New Class", notes = "새로운 클래스를 삽입한다")
  @PostMapping("/test")
  public ClassEntity insertClassTest() {
    classQuestionRepository.deleteAll();
    classRepository.deleteAll();
    classReviewRepository.deleteAll();
    instrumentRepository.deleteAll();
    lectureQuestionRepository.deleteAll();
    lectureRepository.deleteAll();
    sectionRepository.deleteAll();
    tutorRepository.deleteAll();

    ClassReview classReview = new ClassReview(5, "review");
    classReviewRepository.save(classReview);

    Tutor tutor = new Tutor();
    tutorRepository.save(tutor);

    ClassEntity cls = ClassEntity.builder()
        .profile(new Profile("testPath", "testIntro"))
        .title("testTile").tutor(tutor)
        .build();

    cls.appendReview(classReview);
    cls.appendReview(classReview);

    ClassQuestion cq1 = new ClassQuestion("testQuestion1");
    ClassQuestion cq2 = new ClassQuestion("testQuestion2");
    classQuestionRepository.save(cq1);
    classQuestionRepository.save(cq2);

    cls.appendQuestion(cq1);
    cls.appendQuestion(cq2);

    Lecture lecture1 = new Lecture("videoPath1");
    Lecture lecture2 = new Lecture("videoPath2");
    Lecture lecture3 = new Lecture("videoPath3");
    Lecture lecture4 = new Lecture("videoPath4");

    lectureRepository.save(lecture1);
    lectureRepository.save(lecture2);
    lectureRepository.save(lecture3);
    lectureRepository.save(lecture4);

    Section section1 = new Section("sectionName1");
    section1.appendLecture(lecture1);
    sectionRepository.save(section1);

    LectureQuestion lq1 = new LectureQuestion("question1");
    LectureQuestion lq2 = new LectureQuestion("question2");
    LectureQuestion lq3 = new LectureQuestion("question3");
    LectureQuestion lq4 = new LectureQuestion("question4");
    lectureQuestionRepository.save(lq1);
    lectureQuestionRepository.save(lq2);
    lectureQuestionRepository.save(lq3);
    lectureQuestionRepository.save(lq4);

    lecture1.appendQuestion(lq1);
    lecture2.appendQuestion(lq2);

    Section section2 = new Section("sectionName1");
    section2.appendLecture(lecture3);
    section2.appendLecture(lecture4);
    lecture3.appendQuestion(lq3);
    lecture4.appendQuestion(lq4);
    sectionRepository.save(section2);

    InstrumentEntity instrument1 = new InstrumentEntity("inst1");
    InstrumentEntity instrument2 = new InstrumentEntity("inst2");
    instrumentRepository.save(instrument1);
    instrumentRepository.save(instrument2);

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
    tutor.appendClass(cls);
    tutor.appendClass(cls);
    tutorRepository.save(tutor);
    classReviewRepository.save(classReview);

    return cls;
  }
}