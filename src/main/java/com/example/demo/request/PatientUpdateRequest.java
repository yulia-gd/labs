package com.example.demo.request;

import com.example.demo.enums.PatientStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientUpdateRequest {

    private String firstName;


    private String lastName;

    @Email(message = "Email must be valid")
    private String email;

    @Size(min = 10, message = "Phone number must have at least 10 digits")
    private String phone;


    private LocalDate birthDate;


    private PatientStatus patientStatus;

}
