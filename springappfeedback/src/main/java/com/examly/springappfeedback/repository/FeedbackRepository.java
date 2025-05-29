package com.examly.springappfeedback.repository;

import com.examly.springappfeedback.model.Feedback;
import com.examly.springappfeedback.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByUser(User user);
}