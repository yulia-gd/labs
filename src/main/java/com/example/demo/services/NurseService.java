package com.example.demo.services;

import com.example.demo.enities.Nurse;
import com.example.demo.request.NurseUpdateRequest;

import java.util.List;

public interface NurseService {
    Nurse getNurseById(Long id);

    List<Nurse> getAllNurses();

    Nurse addNurse(Nurse nurse);

    Nurse updateNurse(Long id, NurseUpdateRequest updatedNurse);

    void fireNurse(Long id);

    void hireNurse(Long id);
}
