package com.examly.springappcare.service;

import com.examly.springappcare.model.Notification;
import com.examly.springappcare.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository repository;

    public List<Notification> getAllNotifications() { return repository.findAll(); }

    public Notification addNotification(Notification notification) {
        return repository.save(notification);
    }

    public Notification updateNotification(int id, Notification notification) {
        Notification existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        existing.setMessage(notification.getMessage());
        existing.setRecipientRole(notification.getRecipientRole());
        return repository.save(existing);
    }

    public void deleteNotification(int id) { repository.deleteById(id); }
}