package com.ssafy.backend.controller;

import com.ssafy.backend.model.ClassEntity;
import com.ssafy.backend.model.ErrorMessage;
import com.ssafy.backend.model.TutorAccount;
import com.ssafy.backend.model.TutorAward;
import com.ssafy.backend.model.TutorCareer;
import com.ssafy.backend.model.Profile;
import com.ssafy.backend.model.Tutor;
import com.ssafy.backend.model.TutorFeedbackAvailableTime;
import com.ssafy.backend.repository.TutorRepository;
import com.ssafy.backend.util.ErrorType;
import com.ssafy.backend.util.UserSha256;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class TutorController {

  // @Autowired
  // private ClassRepository classRepository;
  @Autowired
  private final TutorRepository tutorRepository;
//   @Autowired
//   private InstrumentRepository instrumentRepository;

  public TutorController(TutorRepository tutorRepository) {
    this.tutorRepository = tutorRepository;
  }

  @ApiOperation(value = "Get All Tutor List", notes = "모든 튜터의 리스트를 반환한다")
  @GetMapping("/tutor")
  public ResponseEntity<?> getAllTutor() {
    List<Tutor> tutorList = tutorRepository.findAll();

    if(!tutorList.isEmpty()) {
      return new ResponseEntity<List<Tutor>>(tutorList, HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Post New Tutor", notes = "새로운 튜터를 삽입한다")
  @PostMapping("/tutor")
  public ResponseEntity<?> insertTutor(@RequestBody Tutor newTutor) {
    Tutor tutor = new Tutor(newTutor); // Test용 코드 아래가 진퉁

    tutorRepository.save(tutor);

    return new ResponseEntity<>(tutor, HttpStatus.OK);
  }

  @ApiOperation(value = "Get Tutor", notes = "ObjectID로 부터 튜터를 가져와 반환한다")
  @GetMapping("/tutor/{id}")
  public ResponseEntity<?> getOneTutor(@PathVariable ObjectId id) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()){
      return new ResponseEntity<> (tutor.get(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Update Tutor", notes = "ObjectID로 부터 튜터를 변경한다")
  @PutMapping("/tutor/{id}")
  public ResponseEntity<?> updateOneTutor(@PathVariable ObjectId id, @RequestBody Tutor newTutor) {

    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()){
      tutor.get().setName(newTutor.getName());
      tutor.get().setNickname(newTutor.getNickname());
      tutor.get().setTutorProfile(newTutor.getTutorProfile());
      tutor.get().setPhoneNumber(newTutor.getPhoneNumber());
      tutor.get().setEmail(newTutor.getEmail());
      tutor.get().setPassword(UserSha256.encrypt(newTutor.getPassword())); // 비밀번호 암호화 어떻게 할지 아직 모름

      tutor.get().setLike(newTutor.getLike());
      tutor.get().setPoint(newTutor.getPoint());

      tutor.get().setTutorCareerList(removeDuplicateCareerByName(tutor.get().getTutorCareerList(), newTutor.getTutorCareerList()));
      tutor.get().setTutorAwardList(removeDuplicateAwardByName(tutor.get().getTutorAwardList(), newTutor.getTutorAwardList()));
      tutor.get().setTutorAccount(newTutor.getTutorAccount());
      tutor.get().setInstList(newTutor.getInstList());

      tutorRepository.save(tutor.get());

      return new ResponseEntity<> (tutor.get(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Delete Tutor", notes = "ObjectID로 부터 튜터를 삭제한다")
  @DeleteMapping("/tutor/{id}")
  public ResponseEntity<?> deleteTutor(@PathVariable ObjectId id) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      tutorRepository.deleteById(id);

      return new ResponseEntity<> (id.toString() + " delete complete", HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Update Password", notes = "튜터의 패스워드를 수정한다")
  @PutMapping("/tutor/{id}/password")
  public ResponseEntity<?> updatePassword(@PathVariable ObjectId id, String password, String newPassword)
  {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()){
      if(tutor.get().getPassword().equals(password)) {
        tutor.get().setPassword(newPassword);
        tutorRepository.save(tutor.get());
        return new ResponseEntity<> ("password update success", HttpStatus.OK);
      }
      else {
        System.out.println("비밀번호 불일치");
        return new ResponseEntity<> ("password update fail", HttpStatus.NOT_MODIFIED);
      }
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  // Career 중복 제거
  public List<TutorCareer> removeDuplicateCareerByName(List<TutorCareer> originalList, List<TutorCareer> newList){
    List<String> newCareerNameList = newList.stream().map(TutorCareer::getName).collect(Collectors.toList());
    List<String> originalCareerNameList = originalList.stream().map(TutorCareer::getName).collect(Collectors.toList());

    int i = 0;
    for (String name : newCareerNameList) {
      if (!originalCareerNameList.contains(name))
        originalList.add(originalCareerNameList.size(), newList.get(i));
      i++;
    }
    return originalList;
  }
  // Award 중복 제거
  public List<TutorAward> removeDuplicateAwardByName(List<TutorAward> originalList, List<TutorAward> newList){
    List<String> newAwardNameList = newList.stream().map(TutorAward::getName).collect(Collectors.toList());
    List<String> originalAwardNameList = originalList.stream().map(TutorAward::getName).collect(Collectors.toList());

    int i = 0;
    for (String name : newAwardNameList) {
      if (!originalAwardNameList.contains(name))
        originalList.add(originalAwardNameList.size(), newList.get(i));
      i++;
    }
    return originalList;
  }

  @ApiOperation(value = "Get TutorFeedbackTime", notes = "튜터의 피드백 가능 시간을 조회한다")
  @GetMapping("/tutor/{id}/feedback/")
  public ResponseEntity<?> GetFeedbackList(@PathVariable ObjectId id, List<Date> feedbackTime) {
    // 유저의 피드백 조회해서 튜터의 피드백을 Get할 때, reservation을 False 표시 해야하나
    // 유저의 피드백 등록 시에, reservation을 True 표시 해야하나
    // 프론트에서 튜터의 피드백과 유저의 피드백을 get해서 비교하는 것인지
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()){
      return new ResponseEntity<> (tutor.get().getTutorFeedbackAvailableTime(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
        ErrorType.TUTOR_NOT_EXIST.toString(),
        HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Post TutorFeedbackTime", notes = "튜터의 피드백 가능 시간을 등록한다")
  @PostMapping("/tutor/{id}/feedback/")
  public ResponseEntity<?> postFeedbackList(@PathVariable ObjectId id, List<Date> feedbackTime) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {
      List<TutorFeedbackAvailableTime> feedbackAvailableTimeList = new ArrayList<>();
      for (Date date : feedbackTime) {
        feedbackAvailableTimeList.add(new TutorFeedbackAvailableTime(date, false));
      }

      tutor.get().setTutorFeedbackAvailableTime(feedbackAvailableTimeList);

      tutorRepository.save(tutor.get());

      return new ResponseEntity<>(tutor.get().getTutorFeedbackAvailableTime(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Get TutorPoint", notes = "튜터의 포인트를 조회한다")
  @GetMapping("/tutor/{id}/point/")
  public ResponseEntity<?> getPoint(@PathVariable ObjectId id) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {
      return new ResponseEntity<>(tutor.get().getPoint(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);

  }

  @ApiOperation(value = "Put TutorPoint", notes = "튜터의 포인트를 수정한다")
  @PutMapping("/tutor/{id}/point/")
  public ResponseEntity<?> putPoint(@PathVariable ObjectId id, Integer point) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      tutor.get().setPoint(tutor.get().getPoint() + point);

      tutorRepository.save(tutor.get());

      return new ResponseEntity<>(tutor.get().getPoint(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Get TutorLike", notes = "튜터의 좋아요를 조회한다")
  @GetMapping("/tutor/{id}/like/")
  public ResponseEntity<?> getLike(@PathVariable ObjectId id) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {
      return new ResponseEntity<>(tutor.get().getLike(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Put TutorLike", notes = "튜터의 좋아요를 수정한다")
  @PutMapping("/tutor/{id}/like/")
  public ResponseEntity<?> putLike(@PathVariable ObjectId id, Integer like) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      tutor.get().setLike(tutor.get().getLike() + like);

      tutorRepository.save(tutor.get());

      return new ResponseEntity<>(tutor.get().getLike(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Get TutorAccount", notes = "튜터의 계좌 정보를 조회한다")
  @GetMapping("/tutor/{id}/account/")
  public ResponseEntity<?> getAccount(@PathVariable ObjectId id) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      return new ResponseEntity<>(tutor.get().getTutorAccount(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Put TutorAccount", notes = "튜터의 계좌 정보를 수정한다")
  @PutMapping("/tutor/{id}/account/")
  public ResponseEntity<?> putAccount(@PathVariable ObjectId id, TutorAccount newAccount) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      tutor.get().setTutorAccount(newAccount);

      tutorRepository.save(tutor.get());
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Get TutorClass", notes = "튜터의 강의 목록을 조회한다")
  @GetMapping("/tutor/{id}/class")
  public ResponseEntity<?> getTutorClass(@PathVariable ObjectId id) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      return new ResponseEntity<>(tutor.get().getTutorClass(), HttpStatus.OK);
    }

    return new ResponseEntity<> (
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
  }

  @ApiOperation(value = "Put TutorClass", notes = "튜터의 강의 목록을 수정한다")
  @PutMapping("/tutor/{id}/class")
  public ResponseEntity<?> putTutorClass(@PathVariable ObjectId id, List<ClassEntity> tutorClass) {
    Optional<Tutor> tutor = tutorRepository.findById(id);

    if(tutor.isPresent()) {

      tutor.get().setTutorClass(tutorClass);

      tutorRepository.save(tutor.get());

      return new ResponseEntity<>(tutor.get().getTutorClass(), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new ErrorMessage(
            ErrorType.TUTOR_NOT_EXIST.toString(),
            HttpStatus.BAD_REQUEST.value()),
        HttpStatus.BAD_REQUEST);
  }
}