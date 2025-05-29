package com.examly.springappcare.service;

import com.examly.springappcare.model.TreatmentRecord;
import com.examly.springappcare.repository.TreatmentRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreatmentRecordServiceImpl implements TreatmentRecordService {

    @Autowired
    private TreatmentRecordRepository repository;

    public List<TreatmentRecord> getAllTreatmentRecords() { return repository.findAll(); }

    public TreatmentRecord getTreatmentRecordById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Treatment record not found"));
    }

    public TreatmentRecord addTreatmentRecord(TreatmentRecord record) {
        return repository.save(record);
    }

    public TreatmentRecord updateTreatmentRecord(int id, TreatmentRecord record) {
        TreatmentRecord existing = getTreatmentRecordById(id);
        existing.setDiagnosis(record.getDiagnosis());
        existing.setMedication(record.getMedication());
        existing.setPetName(record.getPetName());
        return repository.save(existing);
    }

    public void deleteTreatmentRecord(int id) { repository.deleteById(id); }
}
