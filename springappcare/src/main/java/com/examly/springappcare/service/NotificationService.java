package com.examly.springappcare.service;

import com.examly.springappcare.model.Notification;
import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Notification addNotification(Notification notification);
    Notification updateNotification(int id, Notification notification);
    void deleteNotification(int id);
}