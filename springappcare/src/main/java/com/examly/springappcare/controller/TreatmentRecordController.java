package com.examly.springappcare.controller;

import com.examly.springappcare.model.TreatmentRecord;
import com.examly.springappcare.service.TreatmentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/treatments")
public class TreatmentRecordController {

    @Autowired
    private TreatmentRecordService treatmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLINICMANAGER', 'VETERINARIAN')")
    public ResponseEntity<List<TreatmentRecord>> getAllTreatmentRecords() {
        return ResponseEntity.ok(treatmentService.getAllTreatmentRecords());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLINICMANAGER', 'VETERINARIAN', 'PETOWNER')")
    public ResponseEntity<TreatmentRecord> getTreatmentRecordById(@PathVariable int id) {
        return ResponseEntity.ok(treatmentService.getTreatmentRecordById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<TreatmentRecord> addTreatmentRecord(@RequestBody TreatmentRecord record) {
        return ResponseEntity.ok(treatmentService.addTreatmentRecord(record));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<TreatmentRecord> updateTreatmentRecord(@PathVariable int id, @RequestBody TreatmentRecord record) {
        return ResponseEntity.ok(treatmentService.updateTreatmentRecord(id, record));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLINICMANAGER')")
    public ResponseEntity<Void> deleteTreatmentRecord(@PathVariable int id) {
        treatmentService.deleteTreatmentRecord(id);
        return ResponseEntity.ok().build();
    }
}