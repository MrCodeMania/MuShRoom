package com.ssafy.backend.controller;

import com.ssafy.backend.model.User;
import com.ssafy.backend.model.UserClass;
import com.ssafy.backend.model.UserLike;
import com.ssafy.backend.repository.UserRepository;
import com.ssafy.backend.util.PasswordUtil;
import com.ssafy.backend.util.UserSha256;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserRepository repository;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        user.setPassword(UserSha256.encrypt(user.getPassword())); //비밀번호 암호화
        user.setLike(new UserLike(new ArrayList<>(), new ArrayList<>()));
        user.setUserClass(new UserClass(new ArrayList<>(), new ArrayList<>()));
        user.setLectureProgress(new ArrayList<>());
        user.setFeedback(new ArrayList<>());
        repository.insert(user);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User findUser = repository.findByEmail(user.getEmail());
        if (findUser.getPassword().equals(UserSha256.encrypt(user.getPassword()))) {
            return findUser;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("/information")
    public ResponseEntity<?> updateInformation(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (findUser.isPresent()) {
            if (user.getPhone() != null) findUser.get().setPhone(user.getPhone());
            if (user.getNickname() != null) findUser.get().setNickname(user.getNickname());
            if (user.getProfile() != null) findUser.get().setProfile(user.getProfile());
            if (user.getIntroduction() != null) findUser.get().setIntroduction(user.getIntroduction());
            if (user.getInstrument() != null) findUser.get().setInstrument(user.getInstrument());
            repository.save(findUser.get());

            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "비밀번호 변경")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (findUser.isPresent()) {
            findUser.get().setPassword(UserSha256.encrypt(user.getPassword()));
            repository.save(findUser.get());

            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Autowired
    public JavaMailSender javaMailSender;

    public void sendMail(String email, String pwd) {
        SimpleMailMessage s = new SimpleMailMessage();
        s.setTo(email);
        s.setSubject("MuShRoom 비밀번호 찾기");

        StringBuilder sb = new StringBuilder();
        sb.append("임시 비밀번호: ").append(pwd).append("\n");
        sb.append("보안을 위해서 로그인 후 바로 비밀번호를 변경해주세요.");

        s.setText(sb.toString());
        javaMailSender.send(s);
    }
    
    @ApiOperation(value = "아이디 찾기")
    @PostMapping("/email")
    public String findEmail(@RequestBody User user){
        User findUser = repository.findByPhone(user.getPhone());
        if (findUser.getName().equals(user.getName())){
            return findUser.getEmail();
        } else {
            return null;
        }
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping("/password")
    public void findPassword(@RequestBody User user) {
        String newPassword = PasswordUtil.getRandomPassword(8);
        User findUser = repository.findByEmail(user.getEmail());
        findUser.setPassword(UserSha256.encrypt(newPassword));
        repository.save(findUser);

        sendMail(findUser.getEmail(), newPassword);
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/{id}/withdraw")
    public ResponseEntity<?> withdrawal(@PathVariable ObjectId id) {
        Optional<User> findUser = repository.findById(id);
        if (findUser.isPresent()) {
            repository.delete(findUser.get());

            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "찜한 강의 수정")
    @PutMapping("/like/class")
    public ResponseEntity<?> updateClassLike(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (findUser.isPresent()) {
            if (findUser.get().getLike() == null) findUser.get().setLike(user.getLike());
            else findUser.get().getLike().setClassId(user.getLike().getClassId());
            repository.save(findUser.get());

            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "찜한 선생님 수정")
    @PutMapping("/like/tutor")
    public ResponseEntity<?> updateTutorLike(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (findUser.isPresent()) {
            if (findUser.get().getLike() == null) findUser.get().setLike(user.getLike());
            else findUser.get().getLike().setTutorId(user.getLike().getTutorId());
            repository.save(findUser.get());

            return new ResponseEntity<>(findUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ApiOperation(value = "Get User Classes")
    @GetMapping("/{id}/class")
    public UserClass getClass (@PathVariable ObjectId id) {
        Optional<User> findUser = repository.findById(id);
        return findUser.get().getUserClass();
    }

    @ApiOperation(value = "Get User Likes")
    @GetMapping("/{id}/like")
    public UserLike getLike (@PathVariable ObjectId id) {
        Optional<User> findUser = repository.findById(id);
        return findUser.get().getLike();
    }

}
