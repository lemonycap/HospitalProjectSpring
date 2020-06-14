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