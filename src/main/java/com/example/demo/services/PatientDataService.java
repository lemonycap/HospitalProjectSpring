package com.example.demo.services;

import com.example.demo.entity.AppUser;
import com.example.demo.entity.PatientData;
import com.example.demo.entity.Prescription;
import com.example.demo.entity.Role;
import com.example.demo.repos.PatientDataRepo;
import com.example.demo.repos.PrescriptionRepo;
import com.example.demo.repos.RoleRepo;
import com.example.demo.repos.UserRepo;
import com.example.demo.utils.Container;
import com.example.demo.utils.RandomNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.websocket.Session;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PatientDataService {
    private static final Logger logger = LogManager.getLogger(PatientDataService.class);

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    PatientDataRepo patientDataRepo;
    @Autowired
    PrescriptionRepo prescriptionRepo;

    public void refreshPatients() {
        logger.info("adding all patient info to patient data table");
        Role role = roleRepo.findByName("PATIENT");
        List<AppUser> patients = userRepo.getUserByRole(role);

        for (AppUser patient:patients) {
            PatientData user = patientDataRepo.getPatientById(patient.getId());
            if (user == null) {
                logger.info("info about this patient doesn't exist yet, adding this info");
               insertWithQuery(patient);
            }
        }
        checkPrescriptionHistoryOfPatients();
    }

    public void checkPrescriptionHistoryOfPatients() {
        logger.info("checking if patient has prescription history");
        List<PatientData> patientDataList = patientDataRepo.allPatientData();
        for (int i = 0; i < patientDataList.size(); i++) {
            if (patientDataList.get(i).getPrescriptionHistory().isEmpty()) {
                logger.info("creating prescription history");
                createHistoryOfPrescriptions(patientDataList.get(i));
            }
        }
    }

    public void createHistoryOfPrescriptions(PatientData patient) {
        logger.info("prescription history for" + patient.getPatient().getName() + patient.getPatient().getSurname());
        List<Prescription> allPrescriptions = prescriptionRepo.allPrescriptions();
        int numberOfPastPrescriptions = RandomNumber.randNumber(Container.MIN_NUMBER_OF_PRESCRIPTIONS,Container.MAX_NUMBER_OF_PRESCRIPTIONS);
        Set<Prescription> prescriptionHistory = new HashSet<>();
        int prescriptiontoAdd;
        for (int i = 0; i < numberOfPastPrescriptions; i++) {
            prescriptiontoAdd = RandomNumber.randNumber(0, allPrescriptions.size() - 1);
            prescriptionHistory.add(allPrescriptions.get(prescriptiontoAdd));
        }
        patient.setPrescriptionHistory(prescriptionHistory);
        logger.info("prescription history successfully created");
    }

    public void insertWithQuery(AppUser person) {
            logger.info("adding new patient transaction");
            entityManager.joinTransaction();
            entityManager.createNativeQuery("INSERT INTO patient_data (patient_id) VALUES (?)")
                    .setParameter(1, person.getId())
                    .executeUpdate();
            logger.info("adding new patient transaction successfully performed");
    }

}
