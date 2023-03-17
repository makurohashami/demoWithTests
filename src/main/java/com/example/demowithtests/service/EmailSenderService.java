package com.example.demowithtests.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        log.info("Mail to {} sent successfully", toEmail);
    }
}

