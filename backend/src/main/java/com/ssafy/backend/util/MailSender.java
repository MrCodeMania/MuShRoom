package com.ssafy.backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

public class MailSender {

    @Autowired
    public JavaMailSender javaMailSender;

    public void sendMail(String email, String pwd) {
        SimpleMailMessage s = new SimpleMailMessage();
        s.setFrom("xxx@naver.com"); //구글은 필요 없음
        s.setTo(email);
        s.setSubject("MuShRoom 비밀번호 찾기");

        StringBuilder sb = new StringBuilder();
        sb.append("임시 비밀번호: ").append(pwd).append("\n");
        sb.append("보안을 위해서 로그인 후 바로 비밀번호를 변경해주세요.");

        s.setText(sb.toString());
        javaMailSender.send(s);
    }

}
