package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.MedicineList;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.repos.*;
import com.example.demo.utils.Container;
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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class NurseServiceTest {
    @Mock
    PatientDataRepo patientDataRepo;
    @Mock
    UserRepo userRepo;
    @Mock
    PrescriptionRepo prescriptionRepo;
    @Mock
    MedicineListRepo medicineListRepo;

    @InjectMocks
    NurseService nurseService;

    AppUser nurse;
    List<PatientData> noPatients = new ArrayList<>();

    @BeforeEach
    void setUp() {
        nurse = new AppUser();
        nurse.setName("Test");
        nurse.setId(110);
    }

    @Test
    void noPatientsNeedANurse() {

        PatientData patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        patientData1.getPatient().setId(11);
        List<PatientData> patients = new ArrayList<>();
        patients.add(patientData1);

        Mockito.when(patientDataRepo.findPatientWithoutNurse()).thenReturn(noPatients);
        Mockito.when(userRepo.getUserById(nurse.getId())).thenReturn(nurse);
        String checkNull = nurseService.findPatients(nurse.getId());
        assertEquals(checkNull,"no patients need nurse right now");
    }

    @Test
    public void activePatientsCheck() {

        PatientData patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        patientData1.setPatientStatus(1);
        PatientData patientData2 = new PatientData();
        patientData2.setPatient(new AppUser());

        List<PatientData> newPatients = new ArrayList<>();
        newPatients.add(patientData1);
        newPatients.add(patientData2);

        assertEquals(newPatients.size(),2);
        nurseService.checkActivePatients(newPatients);
        assertEquals(newPatients.size(),1);
    }

    @Test
    public void nurseHasMaximumAmountOfPatients () {
        PatientData patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        PatientData patientData2 = new PatientData();
        patientData2.setPatient(new AppUser());
        PatientData patientData3 = new PatientData();
        patientData3.setPatient(new AppUser());
        PatientData patientData4 = new PatientData();
        patientData4.setPatient(new AppUser());
        PatientData patientData5 = new PatientData();
        patientData5.setPatient(new AppUser());

        List<PatientData> nursePatients = new ArrayList<>();
        nursePatients.add(patientData1);
        nursePatients.add(patientData2);
        nursePatients.add(patientData3);
        nursePatients.add(patientData4);
        nursePatients.add(patientData5);

        PatientData patientData6 = new PatientData();
        patientData6.setPatient(new AppUser());
        List<PatientData> newPatients = new ArrayList<>();
        newPatients.add(patientData6);

        Mockito.when(userRepo.getUserById(nurse.getId())).thenReturn(nurse);
        Mockito.when(patientDataRepo.findExistingNursePatients(nurse.getId())).thenReturn(nursePatients);
        Mockito.when(patientDataRepo.findPatientWithoutNurse()).thenReturn(newPatients);

        String checkMax = nurseService.findPatients(nurse.getId());
        assertEquals(checkMax,"You have max amount of patients");

    }

    @Test
    void findPatients() {
        PatientData patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        patientData1.setPatientStatus(1);
        PatientData patientData2 = new PatientData();
        patientData2.setPatient(new AppUser());

        List<PatientData> newPatients = new ArrayList<>();
        newPatients.add(patientData1);
        newPatients.add(patientData2);
        System.out.println(newPatients.size());

        Mockito.when(userRepo.getUserById(nurse.getId())).thenReturn(nurse);
        Mockito.when(patientDataRepo.findPatientWithoutNurse()).thenReturn(newPatients);
        Mockito.when(patientDataRepo.findExistingNursePatients(nurse.getId())).thenReturn(noPatients);

        assertNull(newPatients.get(0).getNurse());
        String findPatient = nurseService.findPatients(nurse.getId());
        System.out.println(newPatients.size());
        assertNotNull(newPatients.get(0).getNurse());
    }

    @Test
    void doPrescriptionMedicine() {
        PatientData patient = new PatientData();
        patient.setPatient(new AppUser());
        Set<Prescription> currentPrescriptions = new HashSet<>();
        Set<PatientData> patientData = new HashSet<>();
        patientData.add(patient);
        Prescription prescription = new Prescription();
        prescription.setPrescriptionClass(Container.PRESCRIPTION_MEDICINE);
        currentPrescriptions.add(prescription);
        patient.setCurrentPrescriptionsList(currentPrescriptions);
        prescription.setPatientCurrentPrescriptions(patientData);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);
        Mockito.when(prescriptionRepo.findPrescriptionById(prescription.getId())).thenReturn(prescription);

        MedicineList medicineList = new MedicineList();
        medicineList.setId(prescription.getId());
        medicineList.setAmount(10);

        Mockito.when(medicineListRepo.medicine(prescription.getName())).thenReturn(medicineList);
        nurseService.doPrescription(patient.getId(),prescription.getId());
        assertEquals(medicineList.getAmount(),9);
    }

    @Test
    public void doOtherPrescription() {
        PatientData patient = new PatientData();
        patient.setPatient(new AppUser());
        Set<Prescription> currentPrescriptions = new HashSet<>();
        Set<PatientData> patientData = new HashSet<>();
        patientData.add(patient);
        Prescription prescription = new Prescription();
        prescription.setPrescriptionClass(Container.PRESCRIPTION_PROCEDURE);
        currentPrescriptions.add(prescription);
        patient.setCurrentPrescriptionsList(currentPrescriptions);
        prescription.setPatientCurrentPrescriptions(patientData);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);
        Mockito.when(prescriptionRepo.findPrescriptionById(prescription.getId())).thenReturn(prescription);

        assertEquals(patient.getCurrentPrescriptionsList().size(),1);
        nurseService.doPrescription(patient.getId(),prescription.getId());
        assertEquals(patient.getCurrentPrescriptionsList().size(),0);
    }
}