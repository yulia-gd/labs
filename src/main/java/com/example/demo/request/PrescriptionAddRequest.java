package com.example.demo.request;

import com.example.demo.enums.PrescriptionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrescriptionAddRequest {

    @NotBlank(message = "Prescription must have description")
    private String description;

    @NotNull(message = "Prescription must have type")
    private PrescriptionType type;
}
