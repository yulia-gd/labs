package com.example.demo.enities;

import com.example.demo.enums.NurseQualification;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nurse")
@Getter
@Setter
public class Nurse extends HospitalEmployee {
    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    @NotNull
    private NurseQualification qualification;

    @Column(name = "department")
    @NotBlank(message = "Nurse must have department")
    private String department;
}

