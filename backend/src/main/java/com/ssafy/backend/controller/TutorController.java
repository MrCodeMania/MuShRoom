package com.ssafy.backend.controller;

import com.ssafy.backend.model.ClassEntity;
import com.ssafy.backend.model.TutorAccount;
import com.ssafy.backend.model.TutorAward;
import com.ssafy.backend.model.TutorCareer;
import com.ssafy.backend.model.Profile;
import com.ssafy.backend.model.Tutor;
import com.ssafy.backend.model.TutorFeedbackAvailableTime;
import com.ssafy.backend.repository.TutorRepository;
import com.ssafy.backend.util.UserSha256;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class TutorController {

  // @Autowired
  // private ClassRepository classRepository;
  @Autowired
  private TutorRepository tutorRepository;
//   @Autowired
//   private InstrumentRepository instrumentRepository;

  @ApiOperation(value = "Get All Tutor List", notes = "모든 튜터의 리스트를 반환한다")
  @GetMapping("/tutor")
  public ResponseEntity<List<Tutor>> getAllTutor() {
    List<Tutor> tutorList = tutorRepository.findAll();
    return new ResponseEntity<List<Tutor>>(tutorList, HttpStatus.OK);
  }

  @ApiOperation(value = "Post New Tutor", notes = "새로운 튜터를 삽입한다")
  @PostMapping("/tutor")
  public void insertTutor(@RequestBody Tutor newTutor) {


    // Test용 코드
    tutorRepository.deleteAll();

    Date now = new Date();

    //////////////////////// Test용 코드
    newTutor.setId(ObjectId.getSmallestWithDate(now));
    newTutor.setPassword("1234");
    newTutor.setEmail("1234");
    newTutor.setPhoneNumber("1234");
    newTutor.setNickname("1234");
    newTutor.setName("1234");
    newTutor.setTutorProfile(new Profile("asdf", "bdcf"));
    List<ObjectId> temp = new ArrayList<>();
    newTutor.setInstList(temp);

    List<TutorCareer> careerTemp = new ArrayList<>();
    TutorCareer a = new TutorCareer("asdf","bcdf","phtj");
    TutorCareer b = new TutorCareer("asfsf","bcdf","phtj");
    careerTemp.add(a);
    careerTemp.add(b);

    newTutor.setTutorCareerList(careerTemp);

    List<TutorAward> _award = new ArrayList<TutorAward>();
    TutorAccount _account = new TutorAccount();
    List<ClassEntity> tutorsClass = new ArrayList<>();

    newTutor.setTutorAwardList(_award);
    newTutor.setTutorAccount(_account);
    newTutor.setTutorClass(tutorsClass);

    Tutor tutor = new Tutor(newTutor); // Test용 코드 아래가 진퉁

    /////////////////////////
    

    // 진퉁

    newTutor.setPassword(UserSha256.encrypt(newTutor.getPassword()));
    int point = 0;
    int like = 0;

//    Builder 이용해서 하다가 포기하고 생성자 하나 생성
//    Tutor tutor = new Tutor(newTutor.getId(), newTutor.getPassword(), newTutor.getEmail(),
//        newTutor.getPhoneNumber(), newTutor.getNickname(), newTutor.getName(), newTutor.getTutorProfile(),
//        newTutor.getInstList());

    tutor.setLike(0);
    tutor.setPoint(0);

    tutorRepository.save(tutor);
  }

  @ApiOperation(value = "Get Tutor", notes = "ObjectID로 부터 튜터를 가져와 반환한다")
  @GetMapping("/tutor/{id}")
  public ResponseEntity<Tutor> getOneTutor(@PathVariable ObjectId id) {
    System.out.println(id.toString());
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    return new ResponseEntity<Tutor> (tutor, HttpStatus.OK);
  }

  @ApiOperation(value = "Update Tutor", notes = "ObjectID로 부터 튜터를 변경한다")
  @PutMapping("/tutor/{id}")
  public void updateOneTutor(@PathVariable ObjectId id, @RequestBody Tutor newTutor) {

    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    tutor.setName(newTutor.getName());
    tutor.setNickname(newTutor.getNickname());
    tutor.setTutorProfile(newTutor.getTutorProfile());
    tutor.setPhoneNumber(newTutor.getPhoneNumber());
    tutor.setEmail(newTutor.getEmail());
    tutor.setPassword(UserSha256.encrypt(newTutor.getPassword()));

    // 업데이트 시, 안 들어가는 것이 맞음
    // tutor.setLike(newTutor.getLike());
    // tutor.setPoint(newTutor.getName());

    tutor.setTutorCareerList(removeDuplicateCareerByName(tutor.getTutorCareerList(), newTutor.getTutorCareerList()));
    tutor.setTutorAwardList(removeDuplicateAwardByName(tutor.getTutorAwardList(), newTutor.getTutorAwardList()));
    tutor.setTutorAccount(newTutor.getTutorAccount());
    tutor.setInstList(newTutor.getInstList());

    tutorRepository.save(tutor);
  }

  @ApiOperation(value = "Delete Tutor", notes = "ObjectID로 부터 튜터를 삭제한다")
  @DeleteMapping("/tutor/{id}")
  public void deleteTutor(@PathVariable ObjectId id) {
    tutorRepository.deleteById(id);
  }

  @ApiOperation(value = "Update Password", notes = "튜터의 패스워드를 수정한다")
  @PutMapping("/tutor/{id}/password")
  public Boolean updatePassword(@PathVariable ObjectId id, String password, String newPassword)
  {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    if(tutor.getPassword().equals(password)) {
      tutor.setPassword(newPassword);
      tutorRepository.save(tutor);
      return true;
    }
    else {
      System.out.println("비밀번호 불일치");
      return false;
    }
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
  public ResponseEntity<List<TutorFeedbackAvailableTime>> GetFeedbackList(@PathVariable ObjectId id, List<Date> feedbackTime) {
    // 유저의 피드백 조회해서 튜터의 피드백을 Get할 때, reservation을 False 표시 해야하나
    // 유저의 피드백 등록 시에, reservation을 True 표시 해야하나
    // 프론트에서 튜터의 피드백과 유저의 피드백을 get해서 비교하는 것인지
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    return new ResponseEntity<List<TutorFeedbackAvailableTime>> (tutor.getTutorFeedbackAvailableTime(), HttpStatus.OK);
  }

  @ApiOperation(value = "Post TutorFeedbackTime", notes = "튜터의 피드백 가능 시간을 등록한다")
  @PostMapping("/tutor/{id}/feedback/")
  public void postFeedbackList(@PathVariable ObjectId id, List<Date> feedbackTime) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    List<TutorFeedbackAvailableTime> feedbackAvailableTimeList = new ArrayList<>();
    for(Date date : feedbackTime) {
      feedbackAvailableTimeList.add(new TutorFeedbackAvailableTime(date, false));
    }

    tutor.setTutorFeedbackAvailableTime(feedbackAvailableTimeList);

    tutorRepository.save(tutor);
  }

  @ApiOperation(value = "Get TutorPoint", notes = "튜터의 포인트를 조회한다")
  @GetMapping("/tutor/{id}/point/")
  public ResponseEntity<Integer> getPoint(@PathVariable ObjectId id) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    return new ResponseEntity<Integer> (tutor.getPoint(), HttpStatus.OK);
  }

  @ApiOperation(value = "Put TutorPoint", notes = "튜터의 포인트를 수정한다")
  @PutMapping("/tutor/{id}/point/")
  public void putPoint(@PathVariable ObjectId id, Integer point) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    tutor.setPoint(tutor.getPoint() + point);

    tutorRepository.save(tutor);
  }

  @ApiOperation(value = "Get TutorLike", notes = "튜터의 좋아요를 조회한다")
  @GetMapping("/tutor/{id}/like/")
  public ResponseEntity<Integer> getLike(@PathVariable ObjectId id) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    return new ResponseEntity<Integer> (tutor.getLike(), HttpStatus.OK);
  }

  @ApiOperation(value = "Put TutorLike", notes = "튜터의 좋아요를 수정한다")
  @PutMapping("/tutor/{id}/like/")
  public void putLike(@PathVariable ObjectId id, Integer like) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    tutor.setPoint(tutor.getPoint() + like);

    tutorRepository.save(tutor);
  }

  @ApiOperation(value = "Get TutorAccount", notes = "튜터의 계좌 정보를 조회한다")
  @GetMapping("/tutor/{id}/account/")
  public ResponseEntity<TutorAccount> getAccount(@PathVariable ObjectId id) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    return new ResponseEntity<TutorAccount> (tutor.getTutorAccount(), HttpStatus.OK);
  }

  @ApiOperation(value = "Put TutorAccount", notes = "튜터의 계좌 정보를 수정한다")
  @PutMapping("/tutor/{id}/account/")
  public void putAccount(@PathVariable ObjectId id, TutorAccount newAccount) {
    Tutor tutor = tutorRepository.findById(id).orElseThrow(() -> {
      return new IllegalArgumentException("해당 사용자는 존재하지 않습니다");
    });

    tutor.setTutorAccount(newAccount);

    tutorRepository.save(tutor);
  }


}