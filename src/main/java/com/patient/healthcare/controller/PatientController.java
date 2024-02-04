package com.patient.healthcare.controller;

import com.patient.healthcare.entity.PaginatedResponse;
import com.patient.healthcare.entity.Patient;
import com.patient.healthcare.serviceimpl.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<Patient>> getAllPatients(
            @RequestParam int page,
            @RequestParam int size) {
        PaginatedResponse<Patient> patients = patientService.getAllPatients(page, size);
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        Patient createdPatient = patientService.createOrUpdatePatient(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        patient.setId(id);
        Patient updatedPatient = patientService.createOrUpdatePatient(patient);
        return ResponseEntity.ok(updatedPatient);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

}
