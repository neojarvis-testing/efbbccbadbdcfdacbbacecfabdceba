package com.examly.springappcare.service;

import com.examly.springappcare.model.Appointment;
import java.util.List;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(int id);
    Appointment addAppointment(Appointment appointment);
    Appointment updateAppointment(int id, Appointment appointment);
    void deleteAppointment(int id);
}
