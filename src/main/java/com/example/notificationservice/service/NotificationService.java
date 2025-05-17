package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.queue.NotificationProducer;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repo;

    @Autowired
    private NotificationProducer producer;

    public void sendNotification(NotificationDto dto) {
        Notification notif = new Notification();
        notif.setUserId(dto.getUserId());
        notif.setType(dto.getType());
        notif.setMessage(dto.getMessage());
        notif.setStatus("pending");
        repo.save(notif);
        producer.send(notif);
    }

    public List<Notification> getUserNotifications(Long userId) {
        return repo.findByUserId(userId);
    }
}
