package com.example.demo.services;

import com.example.demo.entity.MedicineList;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.repos.*;
import com.example.demo.utils.Container;
import com.example.demo.utils.MedicineTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class NurseService {
    public static final int MAX_AMOUNT_OF_PATIENTS = 5;
    @Autowired
    MedicineListRepo medicineListRepo;
    @Autowired
    PatientDataRepo patientDataRepo;
    @Autowired
    DiagnosisRepo diagnosisRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PrescriptionRepo prescriptionRepo;

    public String findPatients(int nurseId) {
        List<PatientData> patientsWithoutNurse = patientDataRepo.findPatientWithoutNurse();
        if (patientsWithoutNurse.size() == 0) {
            return null;
        }
        for (int i = 0; i< patientsWithoutNurse.size(); i++) {
            if (patientsWithoutNurse.get(i).getPatientStatus() == 0) {
                patientsWithoutNurse.remove(patientsWithoutNurse.get(i));
            }
        }
        List<PatientData> nursePatients = patientDataRepo.findExistingNursePatients(nurseId);
        int amountOfPatients = nursePatients.size();
        if (amountOfPatients < MAX_AMOUNT_OF_PATIENTS) {
            patientsWithoutNurse.get(0).setNurse(userRepo.getUserById(nurseId));
        }
        else return "You have max amount of patients";

        return null;
    }

    public void doPrescription(int id, int prescriptionId) {
        PatientData patient = patientDataRepo.getPatientById(id);
        Prescription prescription = prescriptionRepo.findPrescriptionById(prescriptionId);
        if (prescription.getPrescriptionClass().equals(Container.PRESCRIPTION_MEDICINE)) {
            MedicineList medicineList = medicineListRepo.medicine(prescription.getName());
            MedicineTransaction.medicineTransaction(patient,prescription, medicineList);
        }
        Set<Prescription> newList = patient.getCurrentPrescriptionsList();
        newList.remove(prescription);
        patient.setCurrentPrescriptionsList(newList);
        Set<PatientData> patientData = prescription.getPatientCurrentPrescriptions();
        patientData.remove(patient);
        prescription.setPatientCurrentPrescriptions(patientData);
    }
}
