package com.example.demo.repositories;

import com.example.demo.enities.Patient;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    @EntityGraph(attributePaths = {"prescriptions", "diagnoses"})
    @Query("SELECT p FROM Patient p WHERE p.id = :id")
    Optional<Patient> findByIdWithDetails(@Param("id") Long id);

    @EntityGraph(attributePaths = {"prescriptions", "diagnoses"})
    @Query("SELECT p FROM Patient p ")
    List<Patient> findAllWithDetails();

}

