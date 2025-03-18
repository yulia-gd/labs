package com.example.demo.controllers;

import com.example.demo.enities.Nurse;
import com.example.demo.request.NurseUpdateRequest;
import com.example.demo.responce.ApiResponse;
import com.example.demo.services.NurseServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/nurses")
@RequiredArgsConstructor
public class NurseController {

    private final NurseServiceImpl nurseService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getNurseById(@PathVariable Long id) {
        Nurse nurse = nurseService.getNurseById(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Nurse found", nurse));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllNurses() {
        return ResponseEntity.status(OK).body(new ApiResponse("All nurses found", nurseService.getAllNurses()));
    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addNurse(@Valid @RequestBody Nurse nurse) {
        Nurse addedNurse = nurseService.addNurse(nurse);
        return ResponseEntity.status(CREATED).body(new ApiResponse("Nurse added", addedNurse));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateNurse(@PathVariable Long id, @Valid @RequestBody NurseUpdateRequest nurseUpdateRequest) {
        Nurse updatedNurse = nurseService.updateNurse(id, nurseUpdateRequest);
        return ResponseEntity.status(OK).body(new ApiResponse("Nurse updated", updatedNurse));
    }

    @PutMapping("/fire/{id}")
    public ResponseEntity<ApiResponse> fireNurse(@PathVariable Long id) {
        nurseService.fireNurse(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Nurse fired", null));
    }

    @PutMapping("/hire/{id}")
    public ResponseEntity<ApiResponse> hireNurse(@PathVariable Long id) {
        nurseService.hireNurse(id);
        return ResponseEntity.status(OK).body(new ApiResponse("Nurse hired", null));
    }
}

