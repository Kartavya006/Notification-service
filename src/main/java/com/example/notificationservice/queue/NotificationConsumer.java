package com.example.notificationservice.queue;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.EmailService;
import com.example.notificationservice.service.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {
    @Autowired
    private EmailService emailService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private NotificationRepository repo;

    @RabbitListener(queues = "notificationQueue")
    public void process(Notification notif) {
        try {
            switch (notif.getType()) {
                case "email" -> emailService.send(notif);
                case "sms" -> smsService.send(notif);
                default -> {}
            }
            notif.setStatus("sent");
        } catch (Exception e) {
            notif.setStatus("failed");
        }
        repo.save(notif);
    }
}

