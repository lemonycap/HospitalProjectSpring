package com.example.demo.utils;

import com.example.demo.entity.MedicineList;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.repos.MedicineListRepo;
import com.example.demo.repos.PrescriptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Transactional
public class MedicineTransaction {

    public static void medicineTransaction(PatientData patient, Prescription prescription, MedicineList medicineList) {

        medicineList.setAmount(medicineList.getAmount() - 1);
        Set<Prescription> newList = patient.getCurrentPrescriptionsList();
        newList.remove(prescription);
        patient.setCurrentPrescriptionsList(newList);
        Set<PatientData> patientData = prescription.getPatientCurrentPrescriptions();
        patientData.remove(patient);
        prescription.setPatientCurrentPrescriptions(patientData);
    }
}
