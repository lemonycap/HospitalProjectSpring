package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.repos.PatientDataRepo;
import com.example.demo.repos.PrescriptionRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class PatientDataServiceTest {
    @Mock
    PatientDataRepo patientDataRepo;
    @Mock
    PrescriptionRepo prescriptionRepo;
    @InjectMocks
    PatientDataService patientDataService;

    PatientData patient1 = new PatientData();
    PatientData patient2 = new PatientData();
    List<PatientData> patientDataList = new ArrayList<>();
    List<Prescription> prescriptions = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Prescription prescription = new Prescription();
        Prescription prescription1 = new Prescription();
        Prescription prescription2 = new Prescription();

        prescriptions.add(prescription);
        prescriptions.add(prescription1);
        prescriptions.add(prescription2);
    }

    @AfterEach
    public void tearDown() {
        prescriptions.clear();
    }

    @Test
    void checkPrescriptionHistory() {
        patient1.setPatient(new AppUser());
        patient2.setPatient(new AppUser());

        Set<Prescription> history = new HashSet<>();
        history.add(new Prescription());
        patient1.setPrescriptionHistory(history);
        patient2.setPrescriptionHistory(new HashSet<>());
        patientDataList.add(patient1);
        patientDataList.add(patient2);


        PatientData patientData = new PatientData();
        patientData.setPatient(new AppUser());
        Mockito.when(prescriptionRepo.allPrescriptions()).thenReturn(prescriptions);

        Mockito.when(patientDataRepo.allPatientData()).thenReturn(patientDataList);
        assertTrue(patient2.getPrescriptionHistory().isEmpty());
        patientDataService.checkPrescriptionHistoryOfPatients();
       assertFalse(patient2.getPrescriptionHistory().isEmpty());
    }

    @Test
    void createHistoryOfPrescriptions() {
        PatientData patientData = new PatientData();
        patientData.setPatient(new AppUser());
        Mockito.when(prescriptionRepo.allPrescriptions()).thenReturn(prescriptions);
        assertNull(patientData.getPrescriptionHistory());
        patientDataService.createHistoryOfPrescriptions(patientData);
        assertNotNull(patientData.getPrescriptionHistory());
    }

}