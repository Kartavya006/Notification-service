package com.example.notificationservice.service;

import com.example.notificationservice.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void send(Notification notif) {
        //throw new RuntimeException("Simulated failure");//this deliberately triggers retry for failed notifications
                                                            //after I comment this the request will be made which was being retried
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("guptakg2004@gmail.com");
        message.setSubject("Notification");
        message.setText(notif.getMessage());
        mailSender.send(message);
        System.out.println("Email sent to user " + notif.getUserId());
    }
}
