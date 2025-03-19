package com.example.demo.services;

import com.example.demo.enities.Patient;
import com.example.demo.request.PatientAddRequest;
import com.example.demo.request.PatientUpdateRequest;
import com.example.demo.request.PrescriptionAddRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();


    Patient getPatientById(Long id);

    Patient createPatient(PatientAddRequest patientAddRequest);

    Patient updatePatient(Long id, PatientUpdateRequest updatedPatient);

    @Transactional
    void deletePatient(Long id);

    @Transactional
    Patient dischargePatient(Long patientId);

    @Transactional
    Patient addDiagnosis(Long patientId, Long doctorId, String description);

    @Transactional
    Patient addPrescription(Long patientId, Long doctorId, PrescriptionAddRequest prescriptionToAdd);

    @Transactional
    Patient performPrescription(Long prescriptionId, Long performedById);
}
