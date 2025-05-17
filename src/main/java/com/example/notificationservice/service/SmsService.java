package com.example.notificationservice.service;

import com.example.notificationservice.model.Notification;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void send(Notification notif) {
        System.out.println("Sending SMS to user " + notif.getUserId() + ": " + notif.getMessage());
    }
}