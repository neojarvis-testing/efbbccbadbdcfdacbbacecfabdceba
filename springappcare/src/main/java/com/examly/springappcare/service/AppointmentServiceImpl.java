package com.examly.springappcare.service;


import com.examly.springappcare.model.Appointment;
import com.examly.springappcare.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository repository;

    public List<Appointment> getAllAppointments() { return repository.findAll(); }

    public Appointment getAppointmentById(int id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public Appointment addAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public Appointment updateAppointment(int id, Appointment appointment) {
        Appointment existing = getAppointmentById(id);
        existing.setPetName(appointment.getPetName());
        existing.setAppointmentDate(appointment.getAppointmentDate());
        existing.setReason(appointment.getReason());
        return repository.save(existing);
    }

    public void deleteAppointment(int id) { repository.deleteById(id); }
}
