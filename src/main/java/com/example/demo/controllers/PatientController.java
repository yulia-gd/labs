package com.example.demo.controllers;

import com.example.demo.enities.Patient;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.request.PatientAddRequest;
import com.example.demo.request.PatientUpdateRequest;
import com.example.demo.request.PrescriptionAddRequest;
import com.example.demo.responce.ApiResponse;
import com.example.demo.services.PatientServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(new ApiResponse("Patients retrieved successfully", patients));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(new ApiResponse("Patient retrieved successfully", patient));
    }


    @PostMapping("add")
    public ResponseEntity<ApiResponse> createPatient(@Valid@RequestBody PatientAddRequest patient) {
        Patient createdPatient = patientService.createPatient(patient);
        return ResponseEntity.status(201).body(new ApiResponse("Patient created successfully", createdPatient));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<ApiResponse> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateRequest updatedPatient) {
        Patient updated = patientService.updatePatient(id, updatedPatient);
        return ResponseEntity.ok(new ApiResponse("Patient updated successfully", updated));
    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(new ApiResponse("Patient successfully deleted", null));
    }

    @PatchMapping("/{id}/discharge")
    public ResponseEntity<ApiResponse> dischargePatient(@PathVariable Long id) {
        Patient patient = patientService.dischargePatient(id);
        return ResponseEntity.ok(new ApiResponse("Patient discharged successfully", patient));
    }


    @PostMapping("/{patientId}/diagnoses")
    public ResponseEntity<ApiResponse> addDiagnosis(
            @PathVariable Long patientId,
            @RequestParam Long doctorId,
            @NotBlank(message = "You must add description") @RequestParam String description) {
        Patient patient = patientService.addDiagnosis(patientId, doctorId, description);
        return ResponseEntity.status(201).body(new ApiResponse("Diagnosis added successfully", patient));
    }


    @PostMapping("/{patientId}/prescriptions")
    public ResponseEntity<ApiResponse> addPrescription(
            @PathVariable Long patientId,
            @RequestParam Long doctorId,
            @Valid @RequestBody PrescriptionAddRequest prescription) {
        Patient patient = patientService.addPrescription(patientId, doctorId, prescription);
        return ResponseEntity.status(201).body(new ApiResponse("Prescription added successfully", patient));
    }

    // Виконати рецепт
    @PatchMapping("/prescriptions/{prescriptionId}/perform")
    public ResponseEntity<ApiResponse> performPrescription(
            @PathVariable Long prescriptionId,
            @RequestParam Long performedById) {
        Patient patient = patientService.performPrescription(prescriptionId, performedById);
        return ResponseEntity.ok(new ApiResponse("Prescription performed successfully", patient));
    }
}
