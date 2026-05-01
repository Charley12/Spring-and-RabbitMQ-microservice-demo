package com.springmicroservice.appointment.service;

import com.springmicroservice.appointment.model.Appointment;
import com.springmicroservice.appointment.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        existing.setPatientId(updatedAppointment.getPatientId());
        existing.setDoctorId(updatedAppointment.getDoctorId());
        existing.setAppointmentDate(updatedAppointment.getAppointmentDate());
        existing.setStatus(updatedAppointment.getStatus());
        existing.setNotes(updatedAppointment.getNotes());

        return appointmentRepository.save(existing);
    }

    public Appointment patchAppointment(Long id, Map<String, Object> updates) {
        Appointment existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "patientId":
                    existing.setPatientId(((Number) value).longValue());
                    break;
                case "doctorId":
                    existing.setDoctorId(((Number) value).longValue());
                    break;
                case "appointmentDate":
                    existing.setAppointmentDate(LocalDateTime.parse((String) value));
                    break;
                case "status":
                    existing.setStatus((String) value);
                    break;
                case "notes":
                    existing.setNotes((String) value);
                    break;
                default:
                    // Ignore unknown fields
                    break;
            }
        });

        return appointmentRepository.save(existing);
    }

    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found with id: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
