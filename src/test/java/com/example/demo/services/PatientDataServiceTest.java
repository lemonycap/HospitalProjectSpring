package com.example.demo.services;

import com.example.demo.entity.PatientData;
import com.example.demo.entity.Role;
import com.example.demo.repos.PatientDataRepo;
import com.example.demo.repos.PrescriptionRepo;
import com.example.demo.repos.RoleRepo;
import com.example.demo.repos.UserRepo;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PatientDataServiceTest {


    @MockBean
    UserRepo userRepo;
    @MockBean
    RoleRepo roleRepo;
    @MockBean
    PatientDataRepo patientDataRepo;
    @MockBean
    PrescriptionRepo prescriptionRepo;
    @Spy
    PatientDataService patientDataService;
    PatientData patient1;
    PatientData patient2;
    PatientData patient3;

    @Mock
    List<PatientData> mockList = patientDataRepo.allPatientData();

    @Before
    public void init()  throws Exception {
       patientDataService = new PatientDataService();
    }

    @Test
    void refreshPatients() {
        patientDataService.refreshPatients();
        Mockito.verify(roleRepo, Mockito.times(1)).findByName("PATIENT");
    }

    @Test
    void checkPrescriptionHistoryOfPatientsIfNoNewPatients() {
        when (mockList.size()).thenReturn(10);
        Mockito.verify(patientDataRepo, Mockito.times(10)).allPatientData();
      //  Mockito.verify(prescriptionRepo, Mockito.times(0)).allPrescriptions();
    }

    @Test
    void checkPrescriptionHistoryOfPatientsIfNewPatients() {
        PatientData patientData = new PatientData();
        /*Mockito.doReturn(patientData)
                .when()
                .allPatientData();
        patientDataService.checkPrescriptionHistoryOfPatients();
        Mockito.verify(patientDataRepo, Mockito.times(1)).allPatientData();
        Mockito.verify(prescriptionRepo, Mockito.times(1)).allPrescriptions();*/
    }

    @Test
    void createHistoryOfPrescriptions() {

    }
}