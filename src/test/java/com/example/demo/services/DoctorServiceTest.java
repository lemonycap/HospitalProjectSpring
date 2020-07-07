package com.example.demo.services;

import com.example.demo.entity.*;
import com.example.demo.repos.*;
import com.example.demo.utils.Container;
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
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class DoctorServiceTest {
    @Mock
    PatientDataRepo patientDataRepo;
    @Mock
    DiagnosisRepo diagnosisRepo;
    @Mock
    UserRepo userRepo;
    @Mock
    PrescriptionRepo prescriptionRepo;
    @Mock
    MedicineListRepo medicineListRepo;

    @InjectMocks
    DoctorService doctorService;

    AppUser doctor;
    List<PatientData> noPatients = new ArrayList<>();
    List<Diagnosis> diagnoses = new ArrayList<>();
    Prescription prescription1 = new Prescription();
    Prescription prescription2 = new Prescription();
    Prescription prescription3 = new Prescription();
    Prescription prescription4 = new Prescription();
    Prescription prescription5 = new Prescription();
    Prescription prescription6 = new Prescription();
    List<Prescription> medicine = new ArrayList<>();
    List<Prescription> procedures = new ArrayList<>();
    List<Prescription> operations = new ArrayList<>();
    Set<Prescription> pastPrescriptions = new HashSet<>();


    @Test
    void noPatientsNeedADoctor() {

        PatientData patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        patientData1.getPatient().setId(11);
        List<PatientData> patients = new ArrayList<>();
        patients.add(patientData1);

        Mockito.when(patientDataRepo.findPatientWithoutDoctor()).thenReturn(noPatients);
        Mockito.when(userRepo.getUserById(doctor.getId())).thenReturn(doctor);
        String checkNull = doctorService.findPatients(doctor.getId());
        assertEquals(checkNull,"No patients need doctor right now");
    }

    @Test
    public void doctorHasMaximumAmountOfPatients () {
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

        List<PatientData> doctorPatients = new ArrayList<>();
        doctorPatients.add(patientData1);
        doctorPatients.add(patientData2);
        doctorPatients.add(patientData3);
        doctorPatients.add(patientData4);
        doctorPatients.add(patientData5);

        PatientData patientData6 = new PatientData();
        patientData6.setPatient(new AppUser());
        List<PatientData> newPatients = new ArrayList<>();
        newPatients.add(patientData6);

        Mockito.when(userRepo.getUserById(doctor.getId())).thenReturn(doctor);
        Mockito.when(patientDataRepo.findExistingDoctorPatients(doctor.getId())).thenReturn(doctorPatients);
        Mockito.when(patientDataRepo.findPatientWithoutDoctor()).thenReturn(newPatients);


        String checkMax = doctorService.findPatients(doctor.getId());
        assertEquals(checkMax,"You have max amount of patients");

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
        doctorService.checkActivePatients(newPatients);
        assertEquals(newPatients.size(),1);

    }

    @Test
    public void findPatients() {
        PatientData patientData1 = new PatientData();
        patientData1.setPatient(new AppUser());
        patientData1.setPatientStatus(1);
        PatientData patientData2 = new PatientData();
        patientData2.setPatient(new AppUser());

        List<PatientData> newPatients = new ArrayList<>();
        newPatients.add(patientData1);
        newPatients.add(patientData2);
        System.out.println(newPatients.size());

        Mockito.when(userRepo.getUserById(doctor.getId())).thenReturn(doctor);
        Mockito.when(patientDataRepo.findPatientWithoutDoctor()).thenReturn(newPatients);
        Mockito.when(patientDataRepo.findExistingDoctorPatients(doctor.getId())).thenReturn(noPatients);

        assertNull(newPatients.get(0).getDoctor());
        String findPatient = doctorService.findPatients(doctor.getId());
        System.out.println(newPatients.size());
        assertNotNull(newPatients.get(0).getDoctor());
    }

    @Test
    void establishDiagnosis() {
        diagnoses.add(new Diagnosis());
        diagnoses.add(new Diagnosis());
        diagnoses.add(new Diagnosis());
        PatientData patient = new PatientData();

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);
        Mockito.when(diagnosisRepo.allDiagnoses()).thenReturn(diagnoses);

        assertNull(patient.getDiagnosis());
        doctorService.establishDiagnosis(patient.getId());
        assertNotNull(patient.getDiagnosis());
    }

    @Test
    void makePrescriptionsLowDiagnosis() {
        PatientData patient = new PatientData();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDifficulty(Container.LOW_DIAGNOSIS);
        patient.setDiagnosis(diagnosis);

        patient.setPrescriptionHistory(pastPrescriptions);
        assertEquals(pastPrescriptions.size(), 2);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);

        assertNull(patient.getCurrentPrescriptionsList());
        doctorService.makePrescriptions(patient.getId());
        assertNotNull(patient.getCurrentPrescriptionsList());
        for (Prescription p : patient.getCurrentPrescriptionsList()) {
            if (p.getPrescriptionClass().equals(Container.PRESCRIPTION_PROCEDURE)
                    || p.getPrescriptionClass().equals(Container.PRESCRIPTION_OPERATION)) {
                fail();
            }
        }
    }

    @Test
    void makePrescriptionsMediumDiagnosis() {
        PatientData patient = new PatientData();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDifficulty(Container.MEDIUM_DIAGNOSIS);
        patient.setDiagnosis(diagnosis);

        patient.setPrescriptionHistory(pastPrescriptions);
        assertEquals(pastPrescriptions.size(), 2);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);

        assertNull(patient.getCurrentPrescriptionsList());
        doctorService.makePrescriptions(patient.getId());
        assertNotNull(patient.getCurrentPrescriptionsList());
        for (Prescription p : patient.getCurrentPrescriptionsList()) {
            if (p.getPrescriptionClass().equals(Container.PRESCRIPTION_OPERATION)) {
                fail();
            }
        }
    }

    @Test
    void makePrescriptionsHardDiagnosis() {
        PatientData patient = new PatientData();
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDifficulty(Container.HARD_DIAGNOSIS);
        patient.setDiagnosis(diagnosis);

        patient.setPrescriptionHistory(pastPrescriptions);
        assertEquals(pastPrescriptions.size(), 2);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);

        assertNull(patient.getCurrentPrescriptionsList());
        doctorService.makePrescriptions(patient.getId());
        assertNotNull(patient.getCurrentPrescriptionsList());
        for (Prescription p : patient.getCurrentPrescriptionsList()) {
            if (p.getPrescriptionClass().equals(Container.PRESCRIPTION_PROCEDURE)) {
                fail();
            }
        }
    }

    @Test
    void prepareActivePrescriptions() {
        Set<Prescription> pastPrescriptions = new HashSet<>();
        Prescription prescription1 = new Prescription();
        prescription1.setName("1");
        Prescription prescription2 = new Prescription();
        prescription2.setName("2");
        Prescription prescription3 = new Prescription();
        prescription3.setName("3");
        pastPrescriptions.add(prescription1);
        pastPrescriptions.add(prescription2);
        assertEquals(pastPrescriptions.size(),2);

        List<Prescription> listToPrepare = new ArrayList<>();
        listToPrepare.add(prescription1);
        listToPrepare.add(prescription2);
        listToPrepare.add(prescription3);
        assertEquals(listToPrepare.size(),3);

        listToPrepare = doctorService.prepareActivePrescriptions(pastPrescriptions,listToPrepare);
        assertEquals(listToPrepare.size(),1);
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
        doctorService.doPrescription(patient.getId(),prescription.getId());
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
        doctorService.doPrescription(patient.getId(),prescription.getId());
        assertEquals(patient.getCurrentPrescriptionsList().size(),0);
    }

    @Test
    void doctorCanReleasePatient() {
        PatientData patient = new PatientData();
        patient.setPatient(new AppUser());
        patient.setPatientStatus(1);
        Prescription prescription = new Prescription();
        prescription.setPrescriptionClass(Container.PRESCRIPTION_PROCEDURE);
        Set<Prescription> currentPrescriptions = new HashSet<>();
        currentPrescriptions.add(prescription);
        patient.setCurrentPrescriptionsList(currentPrescriptions);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);

        assertEquals(patient.getPatientStatus(),1);
        doctorService.releasePatient(patient.getId());
        assertEquals(patient.getPatientStatus(),0);
        assertNull(patient.getDoctor());
        assertNull(patient.getNurse());

    }

    @Test
    void doctorCannotReleasePatient() {
        PatientData patient = new PatientData();
        patient.setPatient(new AppUser());
        patient.setPatientStatus(1);
        Prescription prescription = new Prescription();
        prescription.setPrescriptionClass(Container.PRESCRIPTION_OPERATION);
        Set<Prescription> currentPrescriptions = new HashSet<>();
        currentPrescriptions.add(prescription);
        patient.setCurrentPrescriptionsList(currentPrescriptions);

        Mockito.when(patientDataRepo.getPatientById(patient.getId())).thenReturn(patient);

        assertEquals(patient.getPatientStatus(),1);
        doctorService.releasePatient(patient.getId());
        assertEquals(patient.getPatientStatus(),1);
    }

    @BeforeEach
    public void setUp() {
        doctor = new AppUser();
        doctor.setName("Test");
        doctor.setId(150);

        prescription1.setPrescriptionClass(Container.PRESCRIPTION_MEDICINE);
        prescription2.setPrescriptionClass(Container.PRESCRIPTION_PROCEDURE);
        prescription3.setPrescriptionClass(Container.PRESCRIPTION_OPERATION);
        prescription4.setPrescriptionClass(Container.PRESCRIPTION_MEDICINE);
        prescription5.setPrescriptionClass(Container.PRESCRIPTION_PROCEDURE);
        prescription6.setPrescriptionClass(Container.PRESCRIPTION_OPERATION);

        pastPrescriptions.add(prescription1);
        pastPrescriptions.add(prescription2);
        medicine.add(prescription1);
        medicine.add(prescription4);
        procedures.add(prescription2);
        procedures.add(prescription5);
        operations.add(prescription3);
        operations.add(prescription6);

        Mockito.when(prescriptionRepo.medicine()).thenReturn(medicine);
        Mockito.when(prescriptionRepo.procedures()).thenReturn(procedures);
        Mockito.when(prescriptionRepo.operations()).thenReturn(operations);
    }

    @AfterEach
    public void tearDown() {
        medicine.clear();
        procedures.clear();
        operations.clear();
        pastPrescriptions.clear();
    }
}