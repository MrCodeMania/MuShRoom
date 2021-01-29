package com.ssafy.backend.controller;

import com.ssafy.backend.model.User;
import com.ssafy.backend.repository.UserRepository;
import com.ssafy.backend.util.PasswordUtil;
import com.ssafy.backend.util.UserSha256;
import io.swagger.annotations.ApiOperation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository repository;

    @ApiOperation(value = "회원 가입")
    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        user.setPassword(UserSha256.encrypt(user.getPassword()));
        repository.insert(user);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User findUser = repository.findByEmail(user.getEmail());
//        System.out.println(findUser);
//        System.out.println(findUser.getPassword());
//        System.out.println(UserSha256.encrypt(user.getPassword()));
        if (findUser.getPassword().equals(UserSha256.encrypt(user.getPassword()))) {
//            System.out.println("1");
            return findUser;
        } else {
//            System.out.println("2");
            return null;
        }
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("/updateInfo")
    public void updateInformation(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (user.getPhone() != null) findUser.get().setPhone(user.getPhone());
        if (user.getNickname() != null) findUser.get().setNickname(user.getNickname());
        if (user.getProfile() != null) findUser.get().setProfile(user.getProfile());
        if (user.getIntroduction() != null) findUser.get().setIntroduction(user.getIntroduction());
        if (user.getInstrument() != null) findUser.get().setInstrument(user.getInstrument());
        repository.save(findUser.get());
    }

    @ApiOperation(value = "비밀번호 변경")
    @PostMapping("/updatePwd")
    public void updatePassword(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        findUser.get().setPassword(UserSha256.encrypt(user.getPassword()));
        repository.save(findUser.get());
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

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping("/findPwd")
    public void findPassword(@RequestBody User user) {
        String newPassword = PasswordUtil.getRandomPassword(8);
//        System.out.println(newPassword);
//        System.out.println(UserSha256.encrypt(newPassword));
        User findUser = repository.findByEmail(user.getEmail());
        findUser.setPassword(UserSha256.encrypt(newPassword));
        repository.save(findUser);

        sendMail(findUser.getEmail(), newPassword);
    }

    @ApiOperation(value = "회원 탈퇴")
    @DeleteMapping("/withdraw/{id}")
    public void withdrawal(@PathVariable ObjectId id) {
        Optional<User> findUser = repository.findById(id);
        repository.delete(findUser.get());
//        repository.delete(user);
    }

    @ApiOperation(value = "찜한 강의 수정")
    @PutMapping("/like/class/update")
    public void updateClassLike(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (findUser.get().getLike() == null) findUser.get().setLike(user.getLike());
        else findUser.get().getLike().setClassId(user.getLike().getClassId());
        repository.save(findUser.get());
    }

    @ApiOperation(value = "찜한 선생님 수정")
    @PutMapping("/like/tutor/update")
    public void updateTutorLike(@RequestBody User user) {
        Optional<User> findUser = repository.findById(user.getId());
        if (findUser.get().getLike() == null) findUser.get().setLike(user.getLike());
        else findUser.get().getLike().setTutorId(user.getLike().getTutorId());
        repository.save(findUser.get());
    }

}
