package com.example.notificationservice.queue;

import com.example.notificationservice.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Notification notification) {
        rabbitTemplate.convertAndSend("notificationQueue", notification);
    }
}