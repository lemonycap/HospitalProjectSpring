package com.example.demo.services;

import com.example.demo.entity.MedicineList;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.repos.*;
import com.example.demo.utils.Container;
import com.example.demo.utils.MedicineTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class NurseService {
    public static final int MAX_AMOUNT_OF_PATIENTS = 5;
    private static final Logger logger = LogManager.getLogger(NurseService.class);
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
        logger.info("find patients for nurse");
        List<PatientData> patientsWithoutNurse = patientDataRepo.findPatientWithoutNurse();
        if (patientsWithoutNurse.size() == 0) {
            logger.info("no patients need nurse right now");
            return "no patients need nurse right now";
        }
        patientsWithoutNurse = checkActivePatients(patientsWithoutNurse);

        List<PatientData> nursePatients = patientDataRepo.findExistingNursePatients(nurseId);
        int amountOfPatients = nursePatients.size();
        if (amountOfPatients < MAX_AMOUNT_OF_PATIENTS) {
            patientsWithoutNurse.get(0).setNurse(userRepo.getUserById(nurseId));
            logger.info("patients were successfully found");
        }
        else return "You have max amount of patients";

        return null;
    }

    public List<PatientData> checkActivePatients (List<PatientData> list) {
        for (int i = 0; i< list.size(); i++) {
            if (list.get(i).getPatientStatus() == 0) {
                list.remove(list.get(i));
            }
        }
        return list;
    }

    public void doPrescription(int id, int prescriptionId) {
        logger.info("performing prescriptions");
        PatientData patient = patientDataRepo.getPatientById(id);
        logger.info("patient " + patient.getPatient().getName() + patient.getPatient().getSurname());
        Prescription prescription = prescriptionRepo.findPrescriptionById(prescriptionId);
        if (prescription.getPrescriptionClass().equals(Container.PRESCRIPTION_MEDICINE)) {
            logger.info("performing medicine transaction");
            MedicineList medicineList = medicineListRepo.medicine(prescription.getName());
            MedicineTransaction.medicineTransaction(patient,prescription, medicineList);
        }
        Set<Prescription> newList = patient.getCurrentPrescriptionsList();
        newList.remove(prescription);
        patient.setCurrentPrescriptionsList(newList);
        Set<PatientData> patientData = prescription.getPatientCurrentPrescriptions();
        patientData.remove(patient);
        prescription.setPatientCurrentPrescriptions(patientData);
        logger.info("prescription successfully performed");
    }
}
