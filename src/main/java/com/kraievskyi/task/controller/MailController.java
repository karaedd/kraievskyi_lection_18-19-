package com.kraievskyi.task.controller;

import com.kraievskyi.task.dto.MessageRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendMail(@RequestBody MessageRequestDto messageRequestDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(messageRequestDto.getSubject());
        message.setText(messageRequestDto.getContent());
        message.setTo(messageRequestDto.getEmail());
        message.setFrom("${spring.mail.username}");

        try {
            mailSender.send(message);
            return ResponseEntity.ok("Mail sent successfully.");
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
