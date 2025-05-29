package com.examly.springappcare.repository;

import com.examly.springappcare.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificationRepository extends JpaRepository<Notification, Integer> {}