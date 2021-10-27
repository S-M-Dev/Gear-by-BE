package com.smdev.gearbybe.service.impl;

import com.smdev.gearbybe.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${smdev.email.recovery}")
    private String recoveryEmail;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendRecoveryCode(String code, String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(recoveryEmail);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Recovery code");
        simpleMailMessage.setText(String.format("Your recovery code is `%s`, don't tell it anyone!", code));
        javaMailSender.send(simpleMailMessage);
    }

}
