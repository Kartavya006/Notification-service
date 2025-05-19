package com.example.notificationservice.queue;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import com.example.notificationservice.service.EmailService;
import com.example.notificationservice.service.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
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
//    @Retryable(
//            value = Exception.class,
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 2000)
//    )
    public void process(Notification notif) {
        System.out.println("Received from queue: " + notif.getType() + " => " + notif.getMessage());
        try {
            switch (notif.getType()) {
                case "email" -> emailService.send(notif);
                case "sms" -> smsService.send(notif);
                default -> {
                    throw new IllegalArgumentException("Unknown type: " + notif.getType());
                }
            }
            notif.setStatus("sent");
        } catch (Exception e) {
            notif.setStatus("failed");
            repo.save(notif);
            System.err.println("Notification failed. Retrying...");
            throw e;
        }
        repo.save(notif);
    }
}
