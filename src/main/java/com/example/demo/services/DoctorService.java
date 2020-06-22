package com.example.demo.services;

import com.example.demo.entity.Diagnosis;
import com.example.demo.entity.MedicineList;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.repos.*;
import com.example.demo.utils.Container;
import com.example.demo.utils.MedicineTransaction;
import com.example.demo.utils.RandomNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class DoctorService {
    public static final int MAX_AMOUNT_OF_PATIENTS = 5;
    private static final Logger logger = LogManager.getLogger(DoctorService.class);
    @Autowired
    PatientDataRepo patientDataRepo;
    @Autowired
    DiagnosisRepo diagnosisRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PrescriptionRepo prescriptionRepo;
    @Autowired
    MedicineListRepo medicineListRepo;

    public  String findPatients(int doctorId) {
        logger.info("find patients for doctor");
        List<PatientData> patientsWithoutDoctor = patientDataRepo.findPatientWithoutDoctor();
        if (patientsWithoutDoctor.size() == 0) {
            logger.info("No patients need doctor right now");
            return null;
        }
        for (int i = 0; i< patientsWithoutDoctor.size(); i++) {
            if (patientsWithoutDoctor.get(i).getPatientStatus() == 0) {
                patientsWithoutDoctor.remove(patientsWithoutDoctor.get(i));
            }
        }
        List<PatientData> doctorsPatients = patientDataRepo.findExistingDoctorPatients(doctorId);
        int amountOfPatients = doctorsPatients.size();
        logger.debug("doctor has got " + amountOfPatients  + " patients");
            if (amountOfPatients < MAX_AMOUNT_OF_PATIENTS) {
                patientsWithoutDoctor.get(0).setDoctor(userRepo.getUserById(doctorId));
            }
            else return "You have max amount of patients";

        return null;
    }

    public  void establishDiagnosis(int patientId) {
        logger.info("diagnosis establishment");
        PatientData patient = patientDataRepo.getPatientById(patientId);
        List<Diagnosis> diagnoses = diagnosisRepo.allDiagnoses();
        int number = RandomNumber.randNumber(0, diagnoses.size() - 1);
        logger.debug("number of diagnosis" + number);
        patient.setDiagnosis(diagnoses.get(number));
        logger.info("diagnosis established");
    }

    public void makePrescriptions(int  patientId) {
        logger.info("making prescriptions");
        PatientData patient = patientDataRepo.getPatientById(patientId);
        Set<Prescription> pastPrescriptions = patient.getPrescriptionHistory();
        List<Prescription> medicine = prepareActivePrescriptions(pastPrescriptions,prescriptionRepo.medicine());
        List<Prescription> procedures = prepareActivePrescriptions(pastPrescriptions,prescriptionRepo.procedures());
        List<Prescription> operations = prepareActivePrescriptions(pastPrescriptions,prescriptionRepo.operations());
        int number;
        Set<Prescription> newPrescriptions = new HashSet<>();
        logger.debug("difficulty of diagnosis:" + patient.getDiagnosis().getDifficulty());
        if (patient.getDiagnosis().getDifficulty().equals(Container.LOW_DIAGNOSIS)) {
            number = RandomNumber.randNumber(0, medicine.size() - 1);
            newPrescriptions.add(medicine.get(number));
        }
        else if (patient.getDiagnosis().getDifficulty().equals(Container.MEDIUM_DIAGNOSIS)) {
            number = RandomNumber.randNumber(0, medicine.size() - 1);
            newPrescriptions.add(medicine.get(number));
            number = RandomNumber.randNumber(0, procedures.size() - 1);
            newPrescriptions.add(procedures.get(number));
        }
        else if (patient.getDiagnosis().getDifficulty().equals(Container.HARD_DIAGNOSIS)) {
            number = RandomNumber.randNumber(0, medicine.size() - 1);
            newPrescriptions.add(medicine.get(number));
            number = RandomNumber.randNumber(0, operations.size() - 1);
            newPrescriptions.add(operations.get(number));
        }
        patient.setCurrentPrescriptionsList(newPrescriptions);
        logger.info("prescriptions made");
    }

    public List<Prescription> prepareActivePrescriptions(Set<Prescription> pastPrescriptions, List<Prescription> listToPrepare) {
        List<Prescription> newList = listToPrepare;
        for (int i = 0; i < listToPrepare.size(); i++) {
            if (pastPrescriptions.contains(listToPrepare.get(i))) {
                newList.remove(i);
            }
        }
        return newList;
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
        else {
            Set<Prescription> newList = patient.getCurrentPrescriptionsList();
            newList.remove(prescription);
            patient.setCurrentPrescriptionsList(newList);
            Set<PatientData> patientData = prescription.getPatientCurrentPrescriptions();
            patientData.remove(patient);
            prescription.setPatientCurrentPrescriptions(patientData);
            logger.info("prescription successfully performed");
        }
    }

    public void releasePatient(int id) {
        logger.info("releasing patient");
        PatientData patient = patientDataRepo.getPatientById(id);
        logger.info(patient.getPatient().getName() + patient.getPatient().getSurname());
        boolean abilityToRelease = true;
        for(Prescription prescription: patient.getCurrentPrescriptionsList()) {
            if(prescription.getPrescriptionClass().equals("operations")) {
                logger.info("patient can't be released");
                abilityToRelease = false;
            }
        }
        if (abilityToRelease) {
            patient.setPatientStatus(0);
            patient.setDoctor(null);
            patient.setNurse(null);
            logger.info("patient has been released");
        }
    }

}
