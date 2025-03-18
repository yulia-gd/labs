package com.example.demo.enities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doctor")
@Getter
@Setter
public class Doctor extends HospitalEmployee {
    @NotBlank(message = "Specialization is required")
    @Column(name = "specialization")
    private String specialization;
}

