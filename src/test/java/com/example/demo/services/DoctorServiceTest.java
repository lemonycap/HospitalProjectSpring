package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.Diagnosis;
import com.example.demo.entity.PatientData;
import com.example.demo.repos.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class DoctorServiceTest {
    @MockBean
    PatientDataRepo patientDataRepo;
    @MockBean
    DiagnosisRepo diagnosisRepo;
    @MockBean
    UserRepo userRepo;
    @MockBean
    PrescriptionRepo prescriptionRepo;
    @MockBean
    MedicineListRepo medicineListRepo;
    @MockBean
    RoleRepo roleRepo;



  @Autowired
  DoctorService doctorService;

    PatientData patientData1;

    @Mock
    AppUser doctor;

    @Mock
    List<PatientData> noPatients = new ArrayList<>();
    @Mock
    List<PatientData> patients = new ArrayList<>();
    @Mock
    List<Diagnosis> diagnoses = new ArrayList<>();

    @Before
    public void init() {
        patients.add(new PatientData());
        doctor = new AppUser();
        doctor.setName("Test");
        doctor.setId(150);
        doctor.setRole(roleRepo.findByName("DOCTOR"));
        Mockito.when(patientDataRepo.findExistingDoctorPatients(doctor.getId())).thenReturn(noPatients);
        Mockito.when(patientDataRepo.findPatientWithoutDoctor()).thenReturn(patients);
        Mockito.when(userRepo.getUserById(doctor.getId())).thenReturn(doctor);

        patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        patientData1.getPatient().setId(150);
        Mockito.when(patientDataRepo.getPatientById(patientData1.getPatient().getId())).thenReturn(patientData1);
        Mockito.when(diagnosisRepo.allDiagnoses()).thenReturn(diagnoses);
    }

    @Test
    void findPatients() {
    }

    @Test
    void establishDiagnosis() {

    }

    @Test
    void makePrescriptions() {
    }

    @Test
    void prepareActivePrescriptions() {
    }

    @Test
    void doPrescription() {
    }

    @Test
    void releasePatient() {

    }
}