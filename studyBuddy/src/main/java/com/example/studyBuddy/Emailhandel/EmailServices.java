package com.example.studyBuddy.Emailhandel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailDTO emailDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDTO.getReceiver());
        message.setSubject(emailDTO.getSubject());
        message.setText(emailDTO.getBody());
        mailSender.send(message);

        System.out.println("Email sent");
    }
}
