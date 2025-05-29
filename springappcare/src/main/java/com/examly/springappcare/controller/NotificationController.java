package com.examly.springappcare.controller;

import com.examly.springappcare.model.Notification;
import com.examly.springappcare.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLINICMANAGER', 'VETERINARIAN', 'PETOWNER')")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping
    @PreAuthorize("hasRole('CLINICMANAGER')")
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.addNotification(notification));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('CLINICMANAGER')")
    public ResponseEntity<Notification> updateNotification(@PathVariable int id, @RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.updateNotification(id, notification));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLINICMANAGER')")
    public ResponseEntity<Void> deleteNotification(@PathVariable int id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
}