package com.patient.healthcare.service;

import com.patient.healthcare.entity.Patient;
import com.patient.healthcare.exception.ResourceNotFoundException;
import com.patient.healthcare.reposiory.PatientRepository;
import com.patient.healthcare.serviceimpl.PatientService;
import lombok.Builder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientById() {
        Long patientId = 1L;
        Patient patient = Patient.builder()
                .id(patientId)
                .name("John Doe")
                .age(30)
                .build();

        when(patientRepository.findById(anyLong())).thenReturn(Optional.of(patient));
        Patient retrievedPatient = patientService.getPatientById(patientId);
        verify(patientRepository).findById(patientId);
        assertEquals(patientId, retrievedPatient.getId());
        assertEquals("John Doe", retrievedPatient.getName());
        assertEquals(30, retrievedPatient.getAge());
    }

    @Test
    public void testDeletePatientById() {
        Long patientId = 1L;
        when(patientRepository.existsById(anyLong())).thenReturn(true);
        patientService.deletePatientById(patientId);
        verify(patientRepository).deleteById(patientId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeletePatientByIdNotFound() {
        Long patientId = 1L;
        when(patientRepository.existsById(anyLong())).thenReturn(false);
        patientService.deletePatientById(patientId);
    }

    @Test
    public void testCreateOrUpdatePatient() {
        Long patientId = 1L;
        Patient patient = Patient.builder()
                .id(patientId)
                .name("John Doe")
                .age(30)
                .build();
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);
        Patient savedPatient = patientService.createOrUpdatePatient(patient);
        verify(patientRepository).save(patient);
        assertEquals(1L, savedPatient.getId().longValue());
        assertEquals("John Doe", savedPatient.getName());
        assertEquals(30, savedPatient.getAge());
    }
}
