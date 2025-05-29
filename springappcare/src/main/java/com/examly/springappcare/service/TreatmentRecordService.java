package com.examly.springappcare.service;

import com.examly.springappcare.model.TreatmentRecord;
import java.util.List;

public interface TreatmentRecordService {
    List<TreatmentRecord> getAllTreatmentRecords();
    TreatmentRecord getTreatmentRecordById(int id);
    TreatmentRecord addTreatmentRecord(TreatmentRecord record);
    TreatmentRecord updateTreatmentRecord(int id, TreatmentRecord record);
    void deleteTreatmentRecord(int id);
}