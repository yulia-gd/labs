package com.example.demo.services;

import com.example.demo.enities.Doctor;
import com.example.demo.enums.EmploymentStatus;
import com.example.demo.exceptions.AlreadyFiredException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.request.DoctorUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor addDoctor(Doctor doctor) {
        doctor.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, DoctorUpdateRequest updatedDoctor) {
        Doctor doctorToUpdate = getDoctorById(id);
        updateExistingDoctor(doctorToUpdate, updatedDoctor);
        return doctorRepository.save(doctorToUpdate);
    }

    private void updateExistingDoctor(Doctor doctorToUpdate, DoctorUpdateRequest updatedDoctor) {
        if(updatedDoctor.getFirstName()!=null && !updatedDoctor.getFirstName().isEmpty()) {
            doctorToUpdate.setFirstName(updatedDoctor.getFirstName());
        }
        if(updatedDoctor.getLastName()!=null && !updatedDoctor.getLastName().isEmpty()) {
            doctorToUpdate.setLastName(updatedDoctor.getLastName());
        }
        if(updatedDoctor.getEmail()!=null && !updatedDoctor.getEmail().isEmpty()) {
            doctorToUpdate.setEmail(updatedDoctor.getEmail());
        }
        if(updatedDoctor.getSpecialization()!=null && !updatedDoctor.getSpecialization().isEmpty()) {
            doctorToUpdate.setSpecialization(updatedDoctor.getSpecialization());
        }
    }

    @Override
    public Doctor fireDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        if(doctor.getEmploymentStatus().equals(EmploymentStatus.FIRED)) {
            throw new AlreadyFiredException("Doctor is already fired");
        }
        doctor.setEmploymentStatus(EmploymentStatus.FIRED);
        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public Doctor hireDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        if(doctor.getEmploymentStatus().equals(EmploymentStatus.EMPLOYED)) {
            throw new AlreadyFiredException("Doctor is already employed");
        }
        doctor.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        doctorRepository.save(doctor);
        return doctor;
    }
}
