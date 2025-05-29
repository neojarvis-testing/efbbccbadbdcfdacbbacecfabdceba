package com.examly.springappcare.model;

import jakarta.persistence.*;

@Entity
public class TreatmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String diagnosis;
    private String medication;
    private String petName;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }
}
