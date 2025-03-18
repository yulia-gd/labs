package com.example.demo.services;

import com.example.demo.enities.Doctor;
import com.example.demo.request.DoctorUpdateRequest;
import jakarta.transaction.Transactional;

import java.util.List;

public interface DoctorService {
    Doctor getDoctorById(Long id);

    List<Doctor> getAllDoctors();

    Doctor addDoctor(Doctor doctor);

    Doctor updateDoctor(Long id, DoctorUpdateRequest updatedDoctor);

    @Transactional
    void fireDoctor(Long id);

    void hireDoctor(Long id);
}
