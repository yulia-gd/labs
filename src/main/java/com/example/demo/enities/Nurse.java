package com.example.demo.enities;

import com.example.demo.enums.NurseQualification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nurse")
@Getter
@Setter
public class Nurse extends HospitalEmployee {
    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    private NurseQualification qualification;

    @Column(name = "department")
    private String department;
}

