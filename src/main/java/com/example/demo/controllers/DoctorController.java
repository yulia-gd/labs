package com.example.demo.controllers;


import com.example.demo.enities.Doctor;
import com.example.demo.request.DoctorUpdateRequest;
import com.example.demo.responce.ApiResponse;
import com.example.demo.services.DoctorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);
        return ResponseEntity.status(FOUND).body(new ApiResponse("Doctor found", doctor));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllDoctors() {
        return ResponseEntity.status(FOUND).body(new ApiResponse("All doctors found", doctorService.getAllDoctors()));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addDoctor(@Valid @RequestBody Doctor doctor) {
        Doctor addedDoctor = doctorService.addDoctor(doctor);
        return ResponseEntity.status(CREATED).body(new ApiResponse(
                "Doctor is added. Location: http://localhost:8080/api/v1/doctors/" + addedDoctor.getId(),
                addedDoctor
        ));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctorUpdateRequest);
        return ResponseEntity.ok(new ApiResponse(
                "Doctor is updated. Location: http://localhost:8080/api/v1/doctors/" + updatedDoctor.getId(),
                updatedDoctor
        ));
    }


    @PutMapping("/fire/{id}")
    public ResponseEntity<ApiResponse> fireDoctor(@PathVariable Long id) {
        Doctor doctor = doctorService.fireDoctor(id);
        return ResponseEntity.ok(new ApiResponse("Doctor is fired", doctor));
    }

    @PutMapping("/hire/{id}")
    public ResponseEntity<ApiResponse> hireDoctor(@PathVariable Long id) {
        Doctor doctor = doctorService.hireDoctor(id);
        return ResponseEntity.ok(new ApiResponse("Doctor is hired again", doctor));
    }
}
