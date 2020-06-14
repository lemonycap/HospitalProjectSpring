package com.example.demo.services;

import com.example.demo.entity.AppUser;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @Spy
    DoctorService doctorService;

    PatientData patientData1;

    PatientData patientData2;

    PatientData patientData3;

    PatientData patientData4;
    PatientData patientData5;
    PatientData patientData6;
    @Mock
    AppUser doctor1;
    AppUser doctor2;
    @Mock
    List<PatientData> noPatients = new ArrayList<>();
    @Mock
    List<PatientData> patients = new ArrayList<>();

    @Before
    public void init() {
        doctor1 = new AppUser ();
        doctor1.setRole(roleRepo.findByName("DOCTOR"));
        doctor1.setId(150);
        verify(doctor1).setId(150);
        doctor2 = new AppUser ();
        doctor2.setRole(roleRepo.findByName("DOCTOR"));
        patientData1.setDoctor(userRepo.getUserById(doctor1.getId()));
        patientData1.setPatient(new AppUser());
        verify(patientData1).setPatient(new AppUser());
        patientData2.setDoctor(userRepo.getUserById(doctor1.getId()));
        patientData2.setPatient(new AppUser());
        verify(patientData2).setPatient(new AppUser());
        patientData3.setDoctor(userRepo.getUserById(doctor1.getId()));
        patientData3.setPatient(new AppUser());
        verify(patientData3).setPatient(new AppUser());
        patientData4.setDoctor(userRepo.getUserById(doctor1.getId()));
        patientData4.setPatient(new AppUser());
        verify(patientData4).setPatient(new AppUser());
        patientData5.setDoctor(userRepo.getUserById(doctor1.getId()));
        patientData5.setPatient(new AppUser());
        verify(patientData5).setPatient(new AppUser());
        noPatients.add(new PatientData());
        verify(noPatients).add(new PatientData());
    }

    @Test
    void findPatients() {

        when (patientDataRepo.findPatientWithoutDoctor()).thenReturn(noPatients);
        when(noPatients.size()).thenReturn(4);
        when(patientDataRepo.findExistingDoctorPatients(doctor1.getId())).thenReturn(patients);
        when(patients.size()).thenReturn(5);
        String noPatients = doctorService.findPatients(doctor1.getId());
        assertEquals(noPatients,"You have max amount of patients");
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