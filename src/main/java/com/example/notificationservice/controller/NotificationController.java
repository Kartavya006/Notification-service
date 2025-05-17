package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDto dto) {
        notificationService.sendNotification(dto);
        return ResponseEntity.ok("Notification sent");
    }

    @GetMapping("/users/{id}")
    public List<Notification> getUserNotifications(@PathVariable Long id) {
        return notificationService.getUserNotifications(id);
    }
}

