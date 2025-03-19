package com.example.demo.services;


import com.example.demo.enities.Nurse;
import com.example.demo.enums.EmploymentStatus;
import com.example.demo.enums.NurseQualification;
import com.example.demo.exceptions.AlreadyEmployedException;
import com.example.demo.exceptions.AlreadyFiredException;
import com.example.demo.exceptions.InvalidEnumValueException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.request.NurseUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NurseServiceImpl implements NurseService {

    private final NurseRepository nurseRepository;

    @Override
    public Nurse getNurseById(Long id) {
        return nurseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nurse not found"));
    }

    @Override
    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    @Override
    public Nurse addNurse(Nurse nurse) {
        validateNurseQualification(nurse.getQualification());
        nurse.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        return nurseRepository.save(nurse);
    }

    @Override
    public Nurse updateNurse(Long id, NurseUpdateRequest updatedNurse) {
        Nurse nurseToUpdate = getNurseById(id);
        updateExistingNurse(nurseToUpdate, updatedNurse);
        return nurseRepository.save(nurseToUpdate);
    }

    private void updateExistingNurse(Nurse nurseToUpdate, NurseUpdateRequest updatedNurse) {
        if (updatedNurse.getFirstName() != null && !updatedNurse.getFirstName().isEmpty()) {
            nurseToUpdate.setFirstName(updatedNurse.getFirstName());
        }
        if (updatedNurse.getLastName() != null && !updatedNurse.getLastName().isEmpty()) {
            nurseToUpdate.setLastName(updatedNurse.getLastName());
        }
        if (updatedNurse.getEmail() != null && !updatedNurse.getEmail().isEmpty()) {
            nurseToUpdate.setEmail(updatedNurse.getEmail());
        }
        if (updatedNurse.getPhone() != null && !updatedNurse.getPhone().isEmpty()) {
            nurseToUpdate.setPhone(updatedNurse.getPhone());
        }
        if (updatedNurse.getDepartment() != null && !updatedNurse.getDepartment().isEmpty()) {
            nurseToUpdate.setDepartment(updatedNurse.getDepartment());
        }
        if (updatedNurse.getQualification() != null) {
            validateNurseQualification(updatedNurse.getQualification());
            nurseToUpdate.setQualification(updatedNurse.getQualification());
        }
    }

    private void validateNurseQualification(NurseQualification qualification) {
        boolean isValid = Arrays.asList(NurseQualification.values()).contains(qualification);
        System.out.println(!isValid);
        if (!isValid) {
            throw new InvalidEnumValueException("Invalid qualification: " + qualification);
        }
    }

    @Override
    public Nurse fireNurse(Long id) {
        Nurse nurse = getNurseById(id);
        if(nurse.getEmploymentStatus().equals(EmploymentStatus.FIRED)) {
            throw new AlreadyFiredException("Nurse is already fired");
        }
        nurse.setEmploymentStatus(EmploymentStatus.FIRED);
        nurseRepository.save(nurse);
        return nurse;
    }

    @Override
    public Nurse hireNurse(Long id) {
        Nurse nurse = getNurseById(id);
        if(nurse.getEmploymentStatus().equals(EmploymentStatus.EMPLOYED)) {
            throw new AlreadyEmployedException("Nurse is already hired");
        }
        nurse.setEmploymentStatus(EmploymentStatus.EMPLOYED);
        nurseRepository.save(nurse);
        return nurse;
    }
}
