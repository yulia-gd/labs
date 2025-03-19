package com.example.demo.services;


import com.example.demo.enities.*;
import com.example.demo.enums.EmploymentStatus;
import com.example.demo.enums.PatientStatus;
import com.example.demo.enums.PrescriptionType;
import com.example.demo.exceptions.AlreadyFiredException;
import com.example.demo.exceptions.AlreadyPerformedException;
import com.example.demo.exceptions.PatientIsDischargedException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.HospitalEmployeeRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.PrescriptionRepository;
import com.example.demo.request.PatientAddRequest;
import com.example.demo.request.PatientUpdateRequest;
import com.example.demo.request.PrescriptionAddRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorService doctorService;
    private final PrescriptionRepository prescriptionRepository;
    private final HospitalEmployeeRepository hospitalEmployeeRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }


    @Override
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    @Override
    public Patient createPatient(PatientAddRequest patientAddRequest) {
        Patient patient = new Patient();
        patient.setFirstName(patientAddRequest.getFirstName());
        patient.setLastName(patientAddRequest.getLastName());
        patient.setEmail(patientAddRequest.getEmail());
        patient.setPhone(patientAddRequest.getPhone());
        patient.setBirthDate(patientAddRequest.getBirthDate());
        patient.setPatientStatus(PatientStatus.HOSPITALIZED);
        return patientRepository.save(patient);
    }

    @Override
    public Patient updatePatient(Long id, PatientUpdateRequest updatedPatient) {
        Patient patientToUpdate = getPatientById(id);
        updateExistedPatient(patientToUpdate, updatedPatient);
        return patientRepository.save(patientToUpdate);
    }

    private void updateExistedPatient(Patient patientToUpdate, PatientUpdateRequest updatedPatient){
        if (updatedPatient.getFirstName() != null && !updatedPatient.getFirstName().isEmpty()) {
            patientToUpdate.setFirstName(updatedPatient.getFirstName());
        }
        if (updatedPatient.getLastName() != null && !updatedPatient.getLastName().isEmpty()) {
            patientToUpdate.setLastName(updatedPatient.getLastName());
        }
        if (updatedPatient.getEmail() != null && !updatedPatient.getEmail().isEmpty()) {
            patientToUpdate.setEmail(updatedPatient.getEmail());
        }
        if (updatedPatient.getPhone() != null && !updatedPatient.getPhone().isEmpty()) {
            patientToUpdate.setPhone(updatedPatient.getPhone());
        }
        if (updatedPatient.getBirthDate() != null) {
            patientToUpdate.setBirthDate(updatedPatient.getBirthDate());
        }
        if (updatedPatient.getPatientStatus() != null) {
            patientToUpdate.setPatientStatus(updatedPatient.getPatientStatus());
        }
    }


    @Override
    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepository.delete(patient);
    }

    @Transactional
    @Override
    public Patient dischargePatient(Long patientId) {
        Patient patient = getPatientById(patientId);

        List<Diagnosis> diagnoses = patient.getDiagnoses();
        if (!diagnoses.isEmpty()) {
            Diagnosis lastDiagnosis = diagnoses.getLast();
            patient.setFinalDiagnosis(lastDiagnosis);
        } else {
            patient.setFinalDiagnosis(null);
        }

        patient.setPatientStatus(PatientStatus.DISCHARGED);

        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    @Override
    public Patient addDiagnosis(Long patientId, Long doctorId, String description) {
        Patient patient = getPatientById(patientId);

        if (patient.getPatientStatus() != PatientStatus.HOSPITALIZED) {
            throw new PatientIsDischargedException("Cannot add diagnosis to a discharged patient");
        }
        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor.getEmploymentStatus() == EmploymentStatus.FIRED) {
            throw new AlreadyFiredException("Doctor is terminated and cannot add diagnosis");
        }

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setPatient(patient);
        diagnosis.setDoctor(doctor);
        diagnosis.setDescription(description);
        diagnosis.setDateDiagnosed(LocalDate.now());

        patient.getDiagnoses().add(diagnosis);

        patientRepository.save(patient);
        return patient;
    }


    @Transactional
    @Override
    public Patient addPrescription(Long patientId, Long doctorId, PrescriptionAddRequest prescriptionToAdd) {
        Patient patient =  getPatientById(patientId);

        if (patient.getPatientStatus() != PatientStatus.HOSPITALIZED) {
            throw new PatientIsDischargedException("Cannot add prescription to a discharged patient");
        }

        Doctor doctor = doctorService.getDoctorById(doctorId);

        if (doctor.getEmploymentStatus() == EmploymentStatus.FIRED) {
            throw new AlreadyFiredException("Doctor is terminated and cannot add prescription");
        }


        Prescription prescription = new Prescription();
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setDescription(prescriptionToAdd.getDescription());
        prescription.setType(prescriptionToAdd.getType());
        prescription.setDateIssued(LocalDate.now());

        patient.getPrescriptions().add(prescription);

        patientRepository.save(patient);
        return patient;
    }

    @Transactional
    public Patient performPrescription(Long prescriptionId, Long performedById) {

        Prescription prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(()-> new ResourceNotFoundException("Prescription is not found"));

        if(prescription.getPerformedBy()!=null){
            throw new AlreadyPerformedException("This prescription is already performed");
        }
        Patient patient = prescription.getPatient();


        if (patient.getPatientStatus() == PatientStatus.DISCHARGED) {
            throw new PatientIsDischargedException("Cannot perform prescription for a discharged patient");
        }

        HospitalEmployee performedBy = hospitalEmployeeRepository.findById(performedById)
                .orElseThrow(()-> new ResourceNotFoundException("Hospital employee is not found"));


        if (performedBy.getEmploymentStatus() == EmploymentStatus.FIRED) {
            throw new AlreadyFiredException("Employee is terminated and cannot perform prescription");
        }

        if (performedBy instanceof Nurse) {
            if(prescription.getType()== PrescriptionType.SURGERY){
                throw new RuntimeException("Nurse cant do surgery");
            }
        }

        prescription.setPerformedBy(performedBy);

        patientRepository.save(patient);
        return patient;
    }

}
