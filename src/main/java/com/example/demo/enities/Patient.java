package com.example.demo.enities;

import com.example.demo.enums.PatientStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Patient must have firstname")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Patient must have lastname")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Patient must have email")
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "Patient must have phone number")
    @Size(min = 10, message = "Phone number must consist at list 10 numbers")
    private String phone;

    @Past(message = "Birthday must be in the past")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PatientStatus patientStatus;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn()
    private Diagnosis finalDiagnosis;

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Prescription> prescriptions = new ArrayList<>();

    @OneToMany(mappedBy = "patient", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Diagnosis> diagnoses = new ArrayList<>();

}

