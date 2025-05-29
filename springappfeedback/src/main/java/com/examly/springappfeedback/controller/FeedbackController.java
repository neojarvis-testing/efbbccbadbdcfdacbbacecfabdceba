package com.examly.springappfeedback.controller;

import com.examly.springappfeedback.model.Feedback;
import com.examly.springappfeedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // 14. Get all feedbacks
    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        try {
            return ResponseEntity.ok(feedbackService.getAllFeedbacks());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // 15. Add feedback
    @PostMapping
    public ResponseEntity<?> addFeedback(@RequestBody Feedback feedback) {
        try {
            Feedback savedFeedback = feedbackService.addFeedback(feedback);
            return ResponseEntity.status(201).body(savedFeedback);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Invalid request");
        }
    }

    // 16. Get feedback by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getFeedbacksByUser(@PathVariable int userId) {
        try {
            return ResponseEntity.ok(feedbackService.getFeedbacksByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }
    }

    // 17. Delete feedback
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity<?> deleteFeedback(@PathVariable int feedbackId) {
        try {
            boolean deleted = feedbackService.deleteFeedback(feedbackId);
            if (deleted) {
                return ResponseEntity.ok("Feedback deleted successfully");
            } else {
                return ResponseEntity.status(404).body("Feedback not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Server Error");
        }
    }
}
