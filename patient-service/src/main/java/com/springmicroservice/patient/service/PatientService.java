package com.springmicroservice.patient.service;

import com.springmicroservice.patient.model.Patient;
import com.springmicroservice.patient.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient createPatient(Patient patient) {
        if (patient.getEmail() != null && patientRepository.existsByEmail(patient.getEmail())) {
            throw new IllegalArgumentException("A patient with email " + patient.getEmail() + " already exists.");
        }
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Long id, Patient updatedPatient) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        existing.setFirstName(updatedPatient.getFirstName());
        existing.setLastName(updatedPatient.getLastName());
        existing.setEmail(updatedPatient.getEmail());
        existing.setPhone(updatedPatient.getPhone());
        existing.setDateOfBirth(updatedPatient.getDateOfBirth());
        existing.setGender(updatedPatient.getGender());
        existing.setAddress(updatedPatient.getAddress());
        existing.setMedicalHistory(updatedPatient.getMedicalHistory());

        return patientRepository.save(existing);
    }

    public Patient patchPatient(Long id, Map<String, Object> updates) {
        Patient existing = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName":
                    existing.setFirstName((String) value);
                    break;
                case "lastName":
                    existing.setLastName((String) value);
                    break;
                case "email":
                    existing.setEmail((String) value);
                    break;
                case "phone":
                    existing.setPhone((String) value);
                    break;
                case "gender":
                    existing.setGender((String) value);
                    break;
                case "address":
                    existing.setAddress((String) value);
                    break;
                case "medicalHistory":
                    existing.setMedicalHistory((String) value);
                    break;
                default:
                    // Ignore unknown fields
                    break;
            }
        });

        return patientRepository.save(existing);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }
}
