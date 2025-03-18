package com.example.demo.enities;
import com.example.demo.enums.EmploymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "hospital_employee")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class HospitalEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Firstname is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Column(name = "phone")
    @NotBlank(message = "Phone number is required")
    @Size(min = 10, message = "Phone number must have at least 10 digits")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_status")
    private EmploymentStatus employmentStatus;
}
