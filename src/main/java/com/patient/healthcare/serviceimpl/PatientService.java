package com.patient.healthcare.serviceimpl;

import com.patient.healthcare.entity.PaginatedResponse;
import com.patient.healthcare.entity.Patient;
import com.patient.healthcare.exception.ResourceNotFoundException;
import com.patient.healthcare.reposiory.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    
    @Cacheable(value = "patients", key = "#id")
    public Patient getPatientById(Long id) {
        System.out.println("Getting data from DB");
        return patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id " + id));
    }
    
    @CacheEvict(value = "patients", key = "#id")
    public void deletePatientById(Long id) {
        System.out.println("Delete data from DB");
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient not found with id " + id);
        }
        patientRepository.deleteById(id);
    }
    
    @CachePut(value = "patients", key = "#result.id")
    public Patient createOrUpdatePatient(Patient patient) {
        System.out.println("Save data to DB");
        return patientRepository.save(patient);
    }


    public PaginatedResponse<Patient> getAllPatients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Patient> patientPage = patientRepository.findAll(pageable);

        return new PaginatedResponse<>(
                patientPage.getContent(),
                patientPage.getNumber(),
                patientPage.getSize()
        );
    }
}
