package com.example.demo.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientAddRequest {

    @NotBlank(message = "Patient must have firstname")
    private String firstName;

    @NotBlank(message = "Patient must have lastname")
    private String lastName;


    @NotBlank(message = "Patient must have email")
    @Email(message = "Email must be valid")
    private String email;


    @NotBlank(message = "Patient must have phone number")
    @Size(min = 10, message = "Phone number must consist at list 10 numbers")
    private String phone;

    @Past(message = "Birthday must be in the past")
    private LocalDate birthDate;
}
