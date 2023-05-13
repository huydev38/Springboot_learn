package com.example.service;

import com.example.entity.User;
import com.example.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserRepo userRepo;
    public void testEmail(){
        String to="vuhuonggiang1hai3@gmail.com";
        String subject="Secret message";
        String body="Ich liebe dich";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                StandardCharsets.UTF_8.name()); //ho tro go tieng Viet
        try {
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
//            messageHelper.setFrom("");  //email nguoi gui, khong set cung duoc vi se dung email trong file properties


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public void sendBirthdayEmail(int day, int month){
        List<User> userList = userRepo.searchByBirthdate(day, month);
        List<String> to = new ArrayList<>();
        for (User u : userList
             ) {
            to.add(u.getEmail());
        }
        String subject="Secret message";
        String body="Ich liebe dich";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                StandardCharsets.UTF_8.name()); //ho tro go tieng Viet
        try {
            messageHelper.setTo(to.toString());
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
//            messageHelper.setFrom("");  //email nguoi gui, khong set cung duoc vi se dung email trong file properties


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }
}
