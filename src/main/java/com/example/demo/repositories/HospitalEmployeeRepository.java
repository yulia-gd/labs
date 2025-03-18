package com.example.demo.repositories;

import com.example.demo.enities.HospitalEmployee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalEmployeeRepository extends JpaRepository<HospitalEmployee, Long> {
}
